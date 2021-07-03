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

    @Deprecated
    public void setCorpNo(String corpNo) {
        IHandle handle = new BookHandle(this, corpNo);
        this.setSession(handle.getSession());
    }

}
