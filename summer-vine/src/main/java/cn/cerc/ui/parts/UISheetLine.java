package cn.cerc.ui.parts;

import cn.cerc.core.ISession;
import cn.cerc.db.core.IHandle;
import cn.cerc.mis.language.R;
import cn.cerc.ui.core.Component;
import cn.cerc.ui.core.HtmlWriter;
import cn.cerc.ui.other.UrlMenu;

public class UISheetLine extends UISheet implements IHandle {
    private UrlMenu operaUrl;
    private ISession session;

    public UISheetLine(UIComponent owner) {
        super(owner);
        this.setCaption(R.asString(this, "数据合计"));
    }

    @Override
    public void output(HtmlWriter html) {
        if (getComponents().size() == 0) {
            return;
        }
        html.println("<section");
        if (this.cssStyle != null) {
            html.print(" style=\"%s\"", this.cssStyle);
        }
        if (this.cssClass != null) {
            html.print(" class=\"%s\"", this.cssClass);
        }
        if (this.getId() != null) {
            html.print(" id=\"%s\"", this.getId());
        }
        html.println(">");
        html.print("<div class=\"title\">");
        html.print(this.getCaption());
        if (operaUrl != null) {
            operaUrl.output(html);
        }
        html.println("</div>");
        html.println("<div class=\"contents\">");
        html.println("<ul>");
        for (Component component : getComponents()) {
            if (component instanceof UIComponent) {
                html.print("<li>");
                ((UIComponent) component).output(html);
                html.print("</li>");
            }
        }
        html.println("</ul>");
        html.println("</div>");
        html.println("</section>");
    }

    public UrlMenu getOperaUrl() {
        if (operaUrl == null) {
            operaUrl = new UrlMenu(null);
            operaUrl.setCssStyle("float:right;margin-bottom:0.25em");
        }
        return operaUrl;
    }

    public void setOperaUrl(UrlMenu operaUrl) {
        this.operaUrl = operaUrl;
    }

    @Override
    public ISession getSession() {
        return this.session;
    }

    @Override
    public void setSession(ISession session) {
        this.session = session;
    }
}
