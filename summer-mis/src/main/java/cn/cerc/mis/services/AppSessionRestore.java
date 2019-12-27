package cn.cerc.mis.services;

import cn.cerc.core.DataSet;
import cn.cerc.core.Record;
import cn.cerc.core.TDateTime;
import cn.cerc.db.mysql.SqlQuery;
import cn.cerc.mis.client.IServiceProxy;
import cn.cerc.mis.client.ServiceFactory;
import cn.cerc.mis.core.Application;
import cn.cerc.mis.core.CustomService;
import cn.cerc.mis.core.DataValidateException;
import cn.cerc.mis.core.HandleDefault;
import cn.cerc.mis.core.ISystemTable;
import cn.cerc.mis.core.ServiceException;
import cn.cerc.mis.other.UserNotFindException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppSessionRestore extends CustomService {

    public boolean byUserCode() throws ServiceException, UserNotFindException {
        Record headIn = getDataIn().getHead();
        DataValidateException.stopRun("用户id不允许为空", !headIn.hasValue("userCode"));
        String userCode = headIn.getString("userCode");

        SqlQuery cdsUser = new SqlQuery(this);
        cdsUser.add("select ID_,Code_,RoleCode_,DiyRole_,CorpNo_, Name_ as UserName_,ProxyUsers_");
        cdsUser.add("from %s ", systemTable.getUserInfo());
        cdsUser.add("where Code_= '%s' ", userCode);
        cdsUser.open();
        if (cdsUser.eof()) {
            throw new UserNotFindException(userCode);
        }

        Record headOut = getDataOut().getHead();
        headOut.setField("LoginTime_", TDateTime.Now());
        copyData(cdsUser, headOut);
        return true;
    }

    public boolean byToken() throws ServiceException {
        Record headIn = getDataIn().getHead();
        DataValidateException.stopRun("token不允许为空", !headIn.hasValue("token"));
        String token = headIn.getString("token");

        DataSet dataToken = new DataSet();
        IServiceProxy svrToken = ServiceFactory.get(this, ISystemTable.Public, "ApiTokenInfo.restoreByToken");
        DataValidateException.stopRun(svrToken.getMessage(), !svrToken.exec("Token_", token));
        dataToken.appendDataSet(svrToken.getDataOut());

        if (dataToken.eof()) {
            log.warn("token {} 没有找到！", token);
            HandleDefault sess = (HandleDefault) this.getProperty(null);
            sess.setProperty(Application.token, null);
            return false;
        }

        if (dataToken.getInt("Viability_") <= 0) {
            log.warn("token {} 已失效，请重新登录", token);
            HandleDefault sess = (HandleDefault) this.getProperty(null);
            sess.setProperty(Application.token, null);
            return false;
        }
        String userId = dataToken.getString("UserID_");

        DataSet dataUser = new DataSet();
        IServiceProxy svrUser = ServiceFactory.get(this, ISystemTable.Public, "ApiTokenInfo.restoreByUserId");
        DataValidateException.stopRun(svrUser.getMessage(), !svrUser.exec("UserId_", userId));
        dataUser.appendDataSet(svrUser.getDataOut());

        if (dataUser.eof()) {
            log.warn(String.format("userId %s 没有找到！", userId));
            HandleDefault sess = (HandleDefault) this.getProperty(null);
            sess.setProperty(Application.token, null);
            return false;
        }

        Record headOut = getDataOut().getHead();
        headOut.setField("LoginTime_", dataToken.getDateTime("LoginTime_"));
        headOut.setField("Language_", dataToken.getString("Language_"));
        copyData(dataUser, headOut);
        return true;
    }

    private void copyData(DataSet ds, Record headOut) {
        headOut.setField("UserID_", ds.getString("ID_"));
        headOut.setField("UserCode_", ds.getString("Code_"));
        headOut.setField("UserName_", ds.getString("UserName_"));
        headOut.setField("CorpNo_", ds.getString("CorpNo_"));
        if (ds.getBoolean("DiyRole_"))
            headOut.setField("RoleCode_", ds.getString("Code_"));
        else
            headOut.setField("RoleCode_", ds.getString("RoleCode_"));
        headOut.setField("ProxyUsers_", ds.getString("ProxyUsers_"));
    }

}
