package cn.cerc.ui.parts;

import cn.cerc.core.DataSource;
import cn.cerc.core.Record;
import cn.cerc.ui.core.HtmlWriter;
import cn.cerc.ui.core.SearchSource;
import cn.cerc.ui.core.UIComponent;
import cn.cerc.ui.fields.AbstractField;
import cn.cerc.ui.vcl.UILabel;
import cn.cerc.ui.vcl.UILi;

public class ExpenderPanel extends UILi implements SearchSource {
    protected DataSource source;
    private String hiddenId = "hidden";
    private boolean visible = true;

    public ExpenderPanel(UIComponent owner) {
        super(owner);
        this.setVisible(false);
        // 查找最近的数据源
        UIComponent root = owner;
        while (root != null) {
            if (root instanceof DataSource) {
                this.source = (DataSource) root;
                break;
            }
            root = root.getOwner();
        }
    }

    @Override
    public void output(HtmlWriter html) {
        for (UIComponent child : this) {
            if (child instanceof AbstractField) {
                AbstractField field = (AbstractField) child;
                html.print("<li");
                html.print(" role='%s'", this.hiddenId);
                html.print(" style=\"display: none;\"");
                html.println(">");
                try {
                    field.output(html);
                } catch (Exception e) {
                    html.print(new UILabel(null).setText(e.getMessage()).toString());
                }
                html.println("</li>");
            } else {
                child.output(html);
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public Record getCurrent() {
        return source.getCurrent();
    }

    @Override
    public boolean isReadonly() {
        return source.isReadonly();
    }

    @Override
    public void updateValue(String id, String code) {
        if (this.getOwner() instanceof SearchSource)
            ((SearchSource) this.getOwner()).updateValue(id, code);
    }

    @Override
    public UIComponent addComponent(UIComponent child) {
        if (child instanceof AbstractField)
            ((AbstractField) child).setVisible(this.isVisible());
        super.addComponent(child);
        return this;
    }

    public final String getHiddenId() {
        return hiddenId;
    }

    public final void setHiddenId(String hiddenId) {
        this.hiddenId = hiddenId;
    }

    public void updateField() {
        for (UIComponent child : this) {
            if (child instanceof AbstractField)
                ((AbstractField) child).updateField();
        }
    }

}
