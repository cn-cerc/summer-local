package cn.cerc.mis.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.cerc.db.core.ClassResource;
import cn.cerc.db.core.DataRow;
import cn.cerc.db.core.DataSet;
import cn.cerc.db.core.Datetime;
import cn.cerc.db.core.ISession;
import cn.cerc.db.core.ServiceException;
import cn.cerc.db.mysql.MysqlQuery;
import cn.cerc.mis.SummerMIS;
import cn.cerc.mis.core.CustomService;
import cn.cerc.mis.core.DataValidateException;
import cn.cerc.mis.other.UserNotFindException;
import cn.cerc.mis.security.Permission;

@Permission(Permission.GUEST)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SvrSession extends CustomService {
    private static final Logger log = LoggerFactory.getLogger(SvrSession.class);
    private static final ClassResource res = new ClassResource(SvrSession.class, SummerMIS.ID);

    public boolean byUserCode() throws ServiceException, UserNotFindException {
        DataRow headIn = dataIn().head();
        DataValidateException.stopRun(String.format(res.getString(1, "%s 不允许为空"), "CorpNo_"),
                !headIn.has("CorpNo_"));
        String corpNo = headIn.getString("CorpNo_");

        DataValidateException.stopRun(String.format(res.getString(1, "%s 不允许为空"), "UserCode_"),
                !headIn.has("UserCode_"));
        String userCode = headIn.getString("UserCode_");

        MysqlQuery cdsUser = new MysqlQuery(this);
        cdsUser.add("select ID_,Code_,RoleCode_,DiyRole_,CorpNo_, Name_ as UserName_,ProxyUsers_");
        cdsUser.add("from %s ", systemTable.getUserInfo());
        cdsUser.add("where CorpNo_='%s' and Code_='%s'", corpNo, userCode);
        cdsUser.open();
        if (cdsUser.eof()) {
            throw new UserNotFindException(userCode);
        }

        DataRow headOut = dataOut().head();
        headOut.setValue("LoginTime_", new Datetime());
        copyData(cdsUser, headOut);
        return true;
    }

    /*
     * 1、从 CurrentUser 表中，取出公司别 CorpNo_ 与 UserCode_
     * 
     * 2、再依据 UserCode_ 从Account表取出 RoleCode_
     * 
     */
    public boolean byToken() throws ServiceException {
        DataRow headIn = dataIn().head();
        DataValidateException.stopRun(String.format(res.getString(1, "%s 不允许为空"), "token"), !headIn.has("token"));
        String token = headIn.getString("token");

        MysqlQuery onlineInfo = new MysqlQuery(this);
        onlineInfo.add("select CorpNo_,UserID_,Viability_,LoginTime_,Account_ as UserCode_,Language_ ");
        onlineInfo.add("from %s", systemTable.getCurrentUser());
        onlineInfo.add("where loginID_='%s'", token);
        onlineInfo.open();
        if (onlineInfo.eof()) {
            this.setMessage(String.format("%s can not find in database.", token));
            this.getSession().setProperty(ISession.TOKEN, null);
            return false;
        }

        if (onlineInfo.getInt("Viability_") <= 0 && !"13100154".equals(onlineInfo.getString("UserCode_"))) {
            this.setMessage(String.format("%s died，please login again.", token));
            this.getSession().setProperty(ISession.TOKEN, null);
            return false;
        }

        String userCode = onlineInfo.getString("UserCode_");
        MysqlQuery userInfo = new MysqlQuery(this);
        userInfo.add(
                "select a.ID_,a.Code_,a.DiyRole_,a.RoleCode_,a.CorpNo_,a.Name_ as UserName_,a.ProxyUsers_,b.Type_");
        userInfo.add("from %s a", systemTable.getUserInfo());
        userInfo.add("inner join %s b on a.CorpNo_=b.CorpNo_", systemTable.getBookInfo());
        userInfo.add("where a.Code_='%s'", userCode);
        userInfo.open();
        if (userInfo.eof()) {
            log.warn(String.format("userCode %s 没有找到！", userCode));
            this.getSession().setProperty(ISession.TOKEN, null);
            return false;
        }

        DataRow headOut = dataOut().head();
        headOut.setValue("LoginTime_", onlineInfo.getDatetime("LoginTime_"));
        headOut.setValue("Language_", onlineInfo.getString("Language_"));
        copyData(userInfo, headOut);
        return true;
    }

    private void copyData(DataSet ds, DataRow headOut) {
        headOut.setValue("UserID_", ds.getString("ID_"));
        headOut.setValue("UserCode_", ds.getString("Code_"));
        headOut.setValue("UserName_", ds.getString("UserName_"));
        headOut.setValue("CorpNo_", ds.getString("CorpNo_"));
        if (ds.getBoolean("DiyRole_")) {
            headOut.setValue("RoleCode_", ds.getString("Code_"));
        } else {
            headOut.setValue("RoleCode_", ds.getString("RoleCode_"));
        }
        headOut.setValue("ProxyUsers_", ds.getString("ProxyUsers_"));
        headOut.setValue("Version_", ds.getString("Type_"));
    }

}
