package cn.cerc.ui.parts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.ui.core.HtmlContent;
import cn.cerc.ui.core.HtmlWriter;
import cn.cerc.ui.core.UIComponent;

public class UIContent extends UIComponent {
    private List<HtmlContent> contents = new ArrayList<>();
    private HttpServletRequest request;

    public UIContent(UIDocument owner) {
        super(owner);
        this.setRootLabel("section");
        this.writeProperty("role", "content");
    }

    @Deprecated
    public void append(HtmlContent content) {
        contents.add(content);
    }

    @Override
    public void output(HtmlWriter html) {
        this.beginOutput(html);
        super.output(html);
        for (HtmlContent content : contents) 
            content.output(html);
        this.endOutput(html);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
