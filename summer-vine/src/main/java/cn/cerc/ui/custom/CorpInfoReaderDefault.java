package cn.cerc.ui.custom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.cerc.db.core.DataRow;
import cn.cerc.db.core.IHandle;
import cn.cerc.db.core.Utils;
import cn.cerc.db.mysql.MysqlQuery;
import cn.cerc.mis.cache.CacheResetMode;
import cn.cerc.mis.cache.IMemoryCache;
import cn.cerc.mis.core.ISystemTable;
import cn.cerc.mis.services.ICorpInfoReader;

@Component
public class CorpInfoReaderDefault implements ICorpInfoReader, IMemoryCache {
    @Autowired
    private ISystemTable systemTable;
    private Map<String, DataRow> items = new ConcurrentHashMap<>();
    private String beanName;

    @Override
    public DataRow getCorpInfo(IHandle handle, String corpNo) {
        if (items.containsKey(corpNo))
            return items.get(corpNo);

        synchronized (this) {
            MysqlQuery ds = new MysqlQuery(handle);
            ds.add("select CorpNo_,Type_,ShortName_,Name_,Address_,Tel_,Status_,Industry_,FastTel_,Currency_,");
            ds.add("ManagerPhone_,StartHost_,Contact_,Authentication_,CorpMailbox_,Fax_");
            ds.add("from %s where CorpNo_=N'%s'", systemTable.getBookInfo(), corpNo);
            ds.open();
            if (ds.eof()) {
                items.put(corpNo, new DataRow());
                return null;
            }

            DataRow result = new DataRow();
            result.setValue("CorpNo_", ds.getString("CorpNo_"));
            result.setValue("ShortName_", ds.getString("ShortName_"));
            result.setValue("Name_", ds.getString("Name_"));
            result.setValue("Address_", ds.getString("Address_"));
            result.setValue("Tel_", ds.getString("Tel_"));
            result.setValue("FastTel_", ds.getString("FastTel_"));
            result.setValue("ManagerPhone_", ds.getString("ManagerPhone_"));
            result.setValue("StartHost_", ds.getString("StartHost_"));
            result.setValue("Contact_", ds.getString("Contact_"));
            result.setValue("Authentication_", ds.getString("Authentication_"));
            result.setValue("Industry_", ds.getString("Industry_"));
            result.setValue("Status_", ds.getInt("Status_"));
            result.setValue("Type_", ds.getInt("Type_"));
            result.setValue("Currency_", ds.getString("Currency_"));
            result.setValue("Email_", ds.getString("CorpMailbox_"));
            result.setValue("Fax_", ds.getString("Fax_"));

            items.put(corpNo, result);
            return result;
        }        
    }

    @Override
    public void resetCache(IHandle handle, CacheResetMode resetType, String param) {
        if (!Utils.isEmpty(param)) {
            items.remove(param);
        } else {
            items.clear();
        }
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }

}
