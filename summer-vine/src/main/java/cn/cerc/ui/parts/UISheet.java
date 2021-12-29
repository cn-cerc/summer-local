package cn.cerc.ui.parts;

import cn.cerc.db.core.ClassResource;
import cn.cerc.ui.SummerUI;
import cn.cerc.ui.core.UIComponent;

public class UISheet extends UIComponent {
    private static final ClassResource res = new ClassResource(UISheet.class, SummerUI.ID);

    private String caption = res.getString(1, "(无标题)");
    private String group = res.getString(2, "工具面板");

    public UISheet() {
        this(null);
    }

    public UISheet(UIComponent owner) {
        super(owner);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
