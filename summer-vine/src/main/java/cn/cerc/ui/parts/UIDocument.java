package cn.cerc.ui.parts;

import cn.cerc.ui.core.HtmlWriter;
import cn.cerc.ui.core.UIComponent;
import cn.cerc.ui.mvc.AbstractPage;
import cn.cerc.ui.vcl.UISection;

public class UIDocument extends UIComponent {
    private UISection header; // 可选存在
    private UIContent content; // 必须存在
    @Deprecated
    private UIMessage message; // 必须存在

    public UIDocument(AbstractPage owner) {
        super(owner);
        this.setRootLabel("article");
        this.writeProperty("role", "document");
        content = new UIContent(this);
        content.setRequest(owner.getRequest());

        message = new UIMessage(this);
        this.getComponents().remove(message);
    }

    @Override
    public void output(HtmlWriter html) {
        this.beginOutput(html);
        for (UIComponent item : this) {
            if (item == header) {
                item.output(html);
                break;
            }
        }
        for (UIComponent item : this) {
            if (item != header && item != message) {
                item.output(html);
            }
        }
        for (UIComponent item : this) {
            if (item == message) {
                item.output(html);
                break;
            }
        }
        this.endOutput(html);
    }

    public UISection getHeader() {
        if (header == null) {
            header = new UISection(this);
            header.writeProperty("role", "control");
        }
        return header;
    }

    @Deprecated // 改名为 getHeader
    public UISection getControl() {
        return this.getHeader();
    }

    public UIContent getContent() {
        return content;
    }

    @Deprecated
    public UIMessage getMessage() {
        return message;
    }

}
