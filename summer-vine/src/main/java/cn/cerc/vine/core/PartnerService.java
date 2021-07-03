package cn.cerc.vine.core;

import cn.cerc.db.core.IHandle;
import cn.cerc.mis.client.RemoteService;
import cn.cerc.mis.core.BookHandle;

/**
 * 调用地藤上下游服务
 * 
 * @author ZhangGong
 *
 */
public class PartnerService extends RemoteService {
    private PartnerServer server = new PartnerServer();

    public PartnerService(IHandle handle) {
        super(handle);
        this.setServer(server);
    }

    public PartnerService(IHandle handle, String service) {
        super(handle);
        this.setServer(server);
        this.setService(service);
    }

    @Override
    public boolean exec(Object... args) {
        return super.exec(args);
    }

    @Override
    public String getCorpNo() {
        return server.getCorpNo();
    }

    public void setCorpNo(String corpNo) {
        server.setCorpNo(corpNo);
        IHandle handle = new BookHandle(this, corpNo);
        this.setSession(handle.getSession());
    }

}
