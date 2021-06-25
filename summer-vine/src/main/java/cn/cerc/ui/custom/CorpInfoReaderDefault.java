package cn.cerc.ui.custom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.cerc.core.Record;
import cn.cerc.core.Utils;
import cn.cerc.db.core.IHandle;
import cn.cerc.db.mysql.MysqlQuery;
import cn.cerc.mis.cache.CacheResetMode;
import cn.cerc.mis.cache.IMemoryCache;
import cn.cerc.mis.core.ISystemTable;
import cn.cerc.mis.services.ICorpInfoReader;

@Component
public class CorpInfoReaderDefault implements ICorpInfoReader, IMemoryCache {
    @Autowired
    private ISystemTable systemTable;
    private Map<String, Record> items = new ConcurrentHashMap<>();
    private String beanName;

    @Override
    public Record getCorpInfo(IHandle handle, String corpNo) {
        if (items.containsKey(corpNo))
            return items.get(corpNo);

        synchronized (this) {
            MysqlQuery ds = new MysqlQuery(handle);
            ds.add("select CorpNo_,Type_,ShortName_,Name_,Address_,Tel_,Status_,Industry_,FastTel_,Currency_,");
            ds.add("ManagerPhone_,StartHost_,Contact_,Authentication_,CorpMailbox_,Fax_");
            ds.add("from %s where CorpNo_=N'%s'", systemTable.getBookInfo(), corpNo);
            ds.open();
            if (ds.eof()) {
                items.put(corpNo, null);
                return null;
            }

            Record result = new Record();
            result.setField("CorpNo_", ds.getString("CorpNo_"));
            result.setField("ShortName_", ds.getString("ShortName_"));
            result.setField("Name_", ds.getString("Name_"));
            result.setField("Address_", ds.getString("Address_"));
            result.setField("Tel_", ds.getString("Tel_"));
            result.setField("FastTel_", ds.getString("FastTel_"));
            result.setField("ManagerPhone_", ds.getString("ManagerPhone_"));
            result.setField("StartHost_", ds.getString("StartHost_"));
            result.setField("Contact_", ds.getString("Contact_"));
            result.setField("Authentication_", ds.getString("Authentication_"));
            result.setField("Industry_", ds.getString("Industry_"));
            result.setField("Status_", ds.getInt("Status_"));
            result.setField("Type_", ds.getInt("Type_"));
            result.setField("Currency_", ds.getString("Currency_"));
            result.setField("Email_", ds.getString("CorpMailbox_"));
            result.setField("Fax_", ds.getString("Fax_"));

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
