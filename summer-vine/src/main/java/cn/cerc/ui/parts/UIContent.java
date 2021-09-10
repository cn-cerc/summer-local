package cn.cerc.ui.parts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.ui.core.HtmlContent;
import cn.cerc.ui.core.HtmlWriter;
import cn.cerc.ui.vcl.UISection;

public class UIContent extends UISection {
    private List<HtmlContent> contents = new ArrayList<>();
    private HttpServletRequest request;

    public UIContent(UIDocument owner) {
        super(owner);
        this.writeProperty("role", "content");
    }

    public void append(HtmlContent content) {
        contents.add(content);
    }

    @Override
    public void endOutput(HtmlWriter html) {
        for (HtmlContent content : contents)
            content.output(html);
        super.endOutput(html);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
