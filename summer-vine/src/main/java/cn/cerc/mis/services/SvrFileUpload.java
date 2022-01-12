package cn.cerc.mis.services;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.cerc.db.core.ClassResource;
import cn.cerc.db.core.DataRow;
import cn.cerc.db.core.DataSet;
import cn.cerc.db.core.Datetime;
import cn.cerc.db.core.IUserLanguage;
import cn.cerc.db.mysql.BuildQuery;
import cn.cerc.db.mysql.MysqlQuery;
import cn.cerc.db.mysql.Transaction;
import cn.cerc.db.oss.OssConnection;
import cn.cerc.mis.core.CustomService;
import cn.cerc.mis.core.DataValidateException;
import cn.cerc.mis.language.R;
import cn.cerc.ui.SummerUI;

/**
 * 文件上传服务
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SvrFileUpload extends CustomService implements IUserLanguage {
    private static final ClassResource res = new ClassResource(SvrFileUpload.class, SummerUI.ID);

    /**
     * 文件上传表
     */
    private static final String TABLE_FILEUPLOADS = "file_uploads";

    public boolean search() throws DataValidateException {
        DataRow headIn = dataIn().head();
        DataValidateException.stopRun(res.getString(1, "请指定单号"), !headIn.has("tbNo"));

        BuildQuery f = new BuildQuery(this);
        f.add("select * from %s", TABLE_FILEUPLOADS);
        f.byField("CorpNo_", getCorpNo());
        f.byField("TBNo_", headIn.getString("tbNo"));

        dataOut().appendDataSet(f.open());
        return true;
    }

    public boolean append() throws DataValidateException {
        DataRow headIn = dataIn().head();
        DataValidateException.stopRun(res.getString(2, "上传失败，单别不能为空！"), !headIn.has("tb"));
        DataValidateException.stopRun(res.getString(3, "上传失败，单号不能为空！"), !headIn.has("tbNo"));

        String tb = headIn.getString("tb");
        String tbNo = headIn.getString("tbNo");

        try (Transaction tx = new Transaction(this)) {
            DataSet dataIn = dataIn();
            while (dataIn.fetch()) {
                DataRow current = dataIn.current();

                DataValidateException.stopRun(res.getString(4, "上传失败，文件大小不能为空！"), !current.has("size"));
                DataValidateException.stopRun(res.getString(5, "上传失败，文件名不能为空！"), !current.has("name"));
                DataValidateException.stopRun(res.getString(6, "上传失败，文件路径不能为空！"), !current.has("path"));

                MysqlQuery ds = new MysqlQuery(this);
                ds.add("select * from %s", TABLE_FILEUPLOADS);
                ds.add("where Path_='%s'", current.getString("path").trim());
                ds.setMaximum(1);
                ds.open();
                if (!ds.eof()) {
                    DataValidateException.stopRun(String.format(res.getString(7, "%s 文件已存在！"), ds.getString("Name_")),
                            true);
                }

                ds.append();
                ds.setValue("CorpNo_", getCorpNo());
                ds.setValue("TB_", tb);
                ds.setValue("TBNo_", tbNo);
                ds.setValue("Name_", current.getString("name").trim());
                ds.setValue("Path_", current.getString("path").trim());
                ds.setValue("Size_", current.getInt("size"));
                ds.setValue("AppUser_", getUserCode());
                ds.setValue("AppDate_", new Datetime());
                ds.post();
            }

            return tx.commit();
        }
    }

    public boolean delete() throws DataValidateException {
        DataRow headIn = dataIn().head();
        DataValidateException.stopRun(res.getString(8, "请指定单号！"), !headIn.has("tbNo"));
        DataValidateException.stopRun(res.getString(9, "请指定文件名！"), !headIn.has("name"));

        String tbNo = headIn.getString("tbNo").trim();
        String name = headIn.getString("name").trim();

        try (Transaction tx = new Transaction(this)) {
            MysqlQuery ds = new MysqlQuery(this);
            ds.add("select * from %s", TABLE_FILEUPLOADS);
            ds.add("where CorpNo_='%s'", getCorpNo());
            ds.add("and TBNo_='%s'", tbNo);
            ds.add("and Name_='%s'", name);
            ds.open();
            DataValidateException.stopRun(res.getString(10, "删除失败，文件不存在！"), ds.eof());

            OssConnection oss = (OssConnection) getSession().getProperty(OssConnection.sessionId);

            while (ds.fetch()) {
                oss.delete(ds.getString("Path_"));

                ds.delete();
            }

            return tx.commit();
        }
    }

    @Override
    public String getLanguageId() {
        return R.getLanguageId(this);
    }
}
