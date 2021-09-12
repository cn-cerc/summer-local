package cn.cerc.ui.page;

import cn.cerc.mis.core.IForm;
import cn.cerc.ui.core.JspFile;
import cn.cerc.ui.core.UIComponent;
import cn.cerc.ui.parts.UIFooter;
import cn.cerc.ui.parts.UIHeader;
import cn.cerc.ui.parts.UIToolbar;

public class JspPage extends JspFile {
    // 头部：广告+菜单
    private UIComponent header;
    // FIXME 此处调用不合理，为保障编译通过先保留 2021/3/14
    private UIToolbar toolBar;
    // FIXME 此处调用不合理，为保障编译通过先保留 2021/3/14
    private UIFooter footer;

    public JspPage() {
        super(null);
    }

    public JspPage(IForm owner) {
        super(owner);
    }

    @Deprecated
    public JspPage(IForm owner, String jspFile) {
        super(owner);
        this.setJspFile(jspFile);
    }

    public UIHeader getHeader() {
        if (header == null) {
            header = new UIHeader(this);
        }
        if (!(header instanceof UIHeader))
            return null;
        return (UIHeader) header;
    }

    public UIFooter getFooter() {
        if(footer == null) {
            footer = new UIFooter(this);
            this.add(footer.getId(), footer);
        }
        return footer;
    }

    @Deprecated
    public UIToolbar getToolBar() {
        if (toolBar == null) {
            toolBar = new UIToolbar(this);
            this.add(toolBar.getId(), toolBar);
        }
        return toolBar;
    }

    public void setHeader(UIComponent header) {
        this.header = header;
    }

}
