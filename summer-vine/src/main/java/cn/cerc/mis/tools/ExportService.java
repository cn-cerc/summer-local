package cn.cerc.mis.tools;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.core.ClassResource;
import cn.cerc.core.DataSet;
import cn.cerc.mis.SummerMIS;
import cn.cerc.mis.core.AbstractForm;
import cn.cerc.mis.core.SystemBuffer;
import cn.cerc.mis.excel.output.AccreditException;
import cn.cerc.mis.excel.output.ExportExcel;
import cn.cerc.mis.other.MemoryBuffer;
import cn.cerc.vine.core.PartnerService;
import jxl.write.WriteException;

public class ExportService extends ExportExcel {
    private static final ClassResource res = new ClassResource(ExportService.class, SummerMIS.ID);

    private final String service;
    private final String exportKey;

    private DataSet dataIn;

    public ExportService(AbstractForm owner) {
        super(owner, owner.getResponse());
        this.setSession(owner.getSession());
        HttpServletRequest request = owner.getRequest();
        service = request.getParameter("service");
        exportKey = request.getParameter("exportKey");
        dataIn = new DataSet();
        try (MemoryBuffer buff = new MemoryBuffer(SystemBuffer.User.ExportKey, this.getUserCode(), exportKey)) {
            dataIn.fromJson(buff.getString("data"));
        }
    }

    @Override
    public void export() throws WriteException, IOException, AccreditException {
        if (service == null || "".equals(service)) {
            throw new RuntimeException(String.format(res.getString(1, "错误的调用：%s"), "service is null"));
        }
        if (exportKey == null || "".equals(exportKey)) {
            throw new RuntimeException(String.format(res.getString(1, "错误的调用：%s"), "exportKey is null"));
        }

        PartnerService app = new PartnerService(this);
        app.setService(service);
        app.getDataIn().close();
        app.getDataIn().appendDataSet(dataIn);
        app.getDataIn().getHead().copyValues(dataIn.getHead());
        if (!app.exec()) {
            this.export(app.getMessage());
            return;
        }

        DataSet dataOut = app.getDataOut();
        // 对分类进行处理
        dataOut.first();
        while (dataOut.fetch()) {
            if (dataOut.getBoolean("IsType_")) {
                dataOut.delete();
            }
        }
        this.getTemplate().setDataSet(dataOut);
        super.export();
    }

    public DataSet getDataIn() {
        return dataIn;
    }

    public void setDataIn(DataSet dataIn) {
        this.dataIn = dataIn;
    }

}
