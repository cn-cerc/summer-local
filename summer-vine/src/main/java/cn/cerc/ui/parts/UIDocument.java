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
    public void beginOutput(HtmlWriter html) {
        super.beginOutput(html);
        // 可选
        if (header != null)
            html.println(header.toString());
    }

    @Override
    public void endOutput(HtmlWriter html) {
        // 必须存在
        html.println(message.toString());
        super.endOutput(html);
    }

    public UISection getHeader() {
        if (header == null) {
            header = new UISection(this);
            header.writeProperty("role", "control");
            // 保障其在第一位
            this.getComponents().remove(header);
            this.getComponents().add(0, header);
        }
        return header;
    }

    @Deprecated //改名为 getHeader
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
