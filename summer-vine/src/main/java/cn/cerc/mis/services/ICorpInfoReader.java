package cn.cerc.mis.services;

import cn.cerc.db.core.DataRow;
import cn.cerc.db.core.IHandle;

public interface ICorpInfoReader {

    /**
     * 取得指定的帐套讯息
     */
    DataRow getCorpInfo(IHandle handle, String corpNo);

}
