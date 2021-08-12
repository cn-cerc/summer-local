package cn.cerc.vine.core;

import cn.cerc.core.ClassConfig;
import cn.cerc.db.core.IHandle;
import cn.cerc.mis.client.IServiceServer;
import cn.cerc.vine.SummerVine;

public class PartnerServer implements IServiceServer {
    private static final ClassConfig config = new ClassConfig(PartnerServer.class, SummerVine.ID);
    private String site;

    public PartnerServer() {
        super();
        this.site = config.getClassProperty("site", null);
    }

    @Override
    public String getRequestUrl(IHandle handle, String service) {
        if (site != null) {
            return String.format("%s?corpNo=%s&service=%s", site, handle.getCorpNo(), service);
        } else {
            return null;
        }
    }

    @Override
    public String getToken(IHandle handle) {
        return null;
    }

}