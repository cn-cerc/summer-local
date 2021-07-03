package cn.cerc.vine.core;

import cn.cerc.core.ClassConfig;
import cn.cerc.mis.client.IServiceServer;
import cn.cerc.vine.SummerVine;

public class PartnerServer implements IServiceServer {
    private static final ClassConfig config = new ClassConfig(PartnerServer.class, SummerVine.ID);
    private String site;
    private String corpNo;

    public PartnerServer() {
        super();
        this.site = config.getClassProperty("site", null);
    }

    @Override
    public String getRequestUrl(String service) {
        if (site != null) {
            return String.format("%s?corpNo=%s&service=%s", site, this.corpNo, service);
        } else {
            return null;
        }
    }

    @Override
    public String getToken() {
        return null;
    }

    public String getCorpNo() {
        return corpNo;
    }

    public void setCorpNo(String corpNo) {
        this.corpNo = corpNo;
    }

}
