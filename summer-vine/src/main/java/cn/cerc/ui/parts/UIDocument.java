package cn.cerc.ui.parts;

import cn.cerc.ui.core.HtmlWriter;
import cn.cerc.ui.core.UIComponent;
import cn.cerc.ui.mvc.AbstractPage;

public class UIDocument extends UIComponent {
    @Deprecated
    private UIComponent control; // 可选存在
    private UIContent content; // 必须存在
    private UIMessage message; // 必须存在

    public UIDocument(AbstractPage owner) {
        super(owner);
        this.setRootLabel("article");
        this.writeProperty("role", "document");
        content = new UIContent(this);
        content.setRequest(owner.getRequest());
        message = new UIMessage(this);
    }

    @Override
    public void output(HtmlWriter html) {
        this.beginOutput(html);
        // 可选
        if (control != null) {
            html.println("<section role='control'>");
            html.println(control.toString());
            html.println("</section>");
        }
        // 必须存在
        html.println(content.toString());
        // 必须存在
        html.println(message.toString());
        this.endOutput(html);
    }

    @Deprecated
    public UIComponent getControl() {
        if (control == null) {
            control = new UIComponent(this);
        }
        return control;
    }

    public UIContent getContent() {
        return content;
    }

    public UIMessage getMessage() {
        return message;
    }

}
