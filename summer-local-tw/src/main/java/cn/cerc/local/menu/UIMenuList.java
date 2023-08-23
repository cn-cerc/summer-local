package cn.cerc.local.menu;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.mis.core.IWriter;
import cn.cerc.ui.core.UIComponent;

public class UIMenuList extends UIComponent {
    private List<UIMenuItem> items = new ArrayList<>();

    public UIMenuList(UIComponent owner) {
        super(owner);
    }

    @Override
    public void output(IWriter html) {
        html.print("<ul role='menuList'>");
        for (UIMenuItem item : items) {
            html.print("<li>");
            html.print(item.toString());
            html.print("</li>");
        }
        html.print("</ul>");
    }

    public List<UIMenuItem> getItems() {
        return items;
    }

    public UIMenuItem addItem() {
        UIMenuItem item = new UIMenuItem(this);
        items.add(item);
        return item;
    }
}
