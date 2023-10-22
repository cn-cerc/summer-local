package cn.cerc.local.menu;

import java.util.List;

import cn.cerc.db.core.IHandle;

public interface IAppMenu {

    // 返回系統菜單定義
    MenuItem getItem(String menuId);

    // 返回指定父菜單下的所有子菜單
    List<MenuItem> getList(IHandle handle, String parentId, boolean security);
}
