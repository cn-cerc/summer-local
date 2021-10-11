package cn.cerc.ui.parts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.core.ClassResource;
import cn.cerc.core.DataRow;
import cn.cerc.core.DataSet;
import cn.cerc.mis.cdn.CDN;
import cn.cerc.mis.core.IForm;
import cn.cerc.mis.other.MemoryBuffer;
import cn.cerc.ui.core.HtmlWriter;
import cn.cerc.ui.core.SearchSource;
import cn.cerc.ui.core.UIComponent;
import cn.cerc.ui.fields.AbstractField;
import cn.cerc.ui.fields.ButtonField;
import cn.cerc.ui.fields.ExpendField;
import cn.cerc.ui.other.SearchItem;
import cn.cerc.ui.style.ISearchPanelStyle;
import cn.cerc.ui.vcl.UIForm;
import cn.cerc.ui.vcl.UIImage;
import cn.cerc.ui.vcl.UILabel;
import cn.cerc.ui.vcl.UILi;
import cn.cerc.ui.vcl.UISpan;
import cn.cerc.ui.vcl.UIText;
import cn.cerc.ui.vcl.UIUl;
import cn.cerc.ui.vcl.UIUrl;
import cn.cerc.vine.SummerVine;

public class UIFormSearch extends UIForm implements SearchSource, ISearchPanelStyle {
    private static final ClassResource res = new ClassResource(UIFormSearch.class, SummerVine.ID);
    protected HttpServletRequest request;
    protected List<AbstractField> fields = new ArrayList<>();
    private DataSet dataSet;
    private ButtonsFields buttons;
    private MemoryBuffer buff;
    private UIComponent levelSide;
    private ButtonField submit;
    private boolean readAll;
    private ExpenderPanel expender;
    private UILabel title;
    private UIFirstSearch firstSearch;
    private UIComponent hiddenSpace;
    private UIUl contentSpace;

    public UIFormSearch(UIComponent owner) {
        super(owner);
        this.setId("form1");
        this.setCssClass("search");

        this.request = ((IForm) this.getOrigin()).getRequest();
        request.setAttribute(this.getId(), this);

        this.dataSet = new DataSet();
        dataSet.append();
        this.title = new UILabel(null);
        this.title.setCssClass("ui-title");
        UISpan span = new UISpan(this.title);
        span.setCssClass("ui-title-operate");
        span.setText("");
        this.setSearchTitle(res.getString(1, "搜索查询"));

        this.firstSearch = new UIFirstSearch(this);
        this.firstSearch.setCssClass("firstSearch");

        this.hiddenSpace = new UIComponent(this);
        this.contentSpace = new UIUl(this);
    }

    @Override
    public DataRow getCurrent() {
        return dataSet.getCurrent();
    }

    @Deprecated
    public DataRow getRecord() {
        return getCurrent();
    }

    public void setRecord(DataRow record) {
        getCurrent().copyValues(record, record.getFieldDefs());
        dataSet.setRecNo(dataSet.size());
    }

    @Deprecated
    public void addField(AbstractField field) {
        this.addComponent(field);
    }

    @Override
    public UIComponent addComponent(UIComponent child) {
        if (child instanceof AbstractField) {
            if (child instanceof SearchItem)
                ((SearchItem) child).setSearch(true);

            fields.add((AbstractField) child);
        }
        super.addComponent(child);
        return this;
    }

    public void setSearchTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void beginOutput(HtmlWriter html) {
        readAll();

        title.output(html);
        super.beginOutput(html);
    }

    @Override
    public void output(HtmlWriter html) {
        this.beginOutput(html);

        for (UIComponent child : contentSpace)
            child.setOwner(null);

        for (AbstractField field : fields) {
            if (firstSearch.getComponents().contains(field))
                continue;
            field.setOwner(field.isHidden() ? hiddenSpace : null);
        }
        hiddenSpace.output(html);

        if (firstSearch.getComponentCount() > 0)
            firstSearch.output(html);

        // 输出正常查询字段
        for (AbstractField field : fields) {
            if (firstSearch.getComponents().contains(field))
                continue;
            if (field.isHidden())
                continue;
            if (field instanceof ExpendField)
                this.expender.setHiddenId(((ExpendField) field).getHiddenId());

            UIText mark = field.getMark();
            if (mark != null) {
                UILi li1 = new UILi(null);
                li1.writeProperty("role", field.getId());
                if (field instanceof ExpendField)
                    li1.setCssClass("select");
                li1.setText(field.toString());
                UIUrl url = new UIUrl(li1);
                url.setHref(String.format("javascript:displaySwitch(\"%s\")", field.getId()));
                new UIImage(url).setSrc(CDN.get(AbstractField.getIconConfig()));
                contentSpace.addComponent(li1);
                //
                UILi li2 = new UILi(null);
                li2.writeProperty("role", field.getId()).setCssStyle("display: none;");
                li2.setText(mark.setRootLabel("mark").toString());
                contentSpace.addComponent(li2);
            } else {
                UILi li = new UILi(null);
                li.writeProperty("role", field.getRole());
                if (field instanceof ExpendField)
                    li.setCssClass("select");
                li.setText(field.toString());
                contentSpace.addComponent(li);
            }
        }
        // 输出可折叠字段, 需将之移到最尾部
        if (this.expender != null) {
            contentSpace.removeComponent(expender);
            contentSpace.addComponent(expender);
        }
        contentSpace.output(html);

        if (buttons != null)
            buttons.output(html);

        this.endOutput(html);
    }

    @Override
    public void endOutput(HtmlWriter html) {
        super.endOutput(html);
        if (levelSide != null)
            levelSide.output(html);
    }

    public MemoryBuffer getBuffer() {
        return buff;
    }

    public void setBuffer(MemoryBuffer buff) {
        this.buff = buff;
    }

    public ButtonsFields getButtons() {
        if (buttons == null) {
            buttons = new ButtonsFields(this);
        }
        return buttons;
    }

    public void setLevelSide(UIComponent levelSide) {
        this.levelSide = levelSide;
    }

    public ButtonField readAll() {
        if (readAll) {
            return submit;
        }

        if (buttons == null) {
            return submit;
        }

        submit = null;
        // 取 form submit 按钮
        for (AbstractField field : buttons.getFields()) {
            if (field instanceof ButtonField) {
                ButtonField button = (ButtonField) field;
                String key = button.getField();
                String val = request.getParameter(key);
                if (val != null && val.equals(button.getData())) {
                    submit = button;
                    break;
                }
            }
        }

        // 将用户值或缓存值存入到dataSet中
        for (AbstractField field : this.fields) {
            field.updateField();
        }

        // 将可折叠字段的值存入到dataSet中
        if (this.expender != null)
            this.expender.updateField();

        readAll = true;
        return submit;
    }

    @Override
    public void updateValue(String id, String code) {
        String val = request.getParameter(id);
        if (submit != null) {
            dataSet.setValue(code, val == null ? "" : val);
            if (buff != null) {
                buff.setValue(code, val);
            }
        } else {
            if (val != null) {
                dataSet.setValue(code, val);
            } else if (buff != null && !buff.isNull() && buff.getRecord().exists(code)) {
                dataSet.setValue(code, buff.getString(code));
            }
        }
    }

    public ButtonField getSubmit() {
        return this.submit;
    }

    public ExpenderPanel getExpender() {
        if (this.expender == null)
            this.expender = new ExpenderPanel(this.contentSpace);
        return this.expender;
    }

    @Override
    public boolean isReadonly() {
        return false;
    }

    public class ButtonsFields extends UIComponent implements SearchSource {
        private SearchSource source;

        public ButtonsFields(UIComponent owner) {
            super(owner);
            this.setRootLabel("div");
            // 查找最近的数据源
            UIComponent root = owner;
            while (root != null) {
                if (root instanceof SearchSource) {
                    this.source = (SearchSource) root;
                    break;
                }
                root = root.getOwner();
            }
        }

        public List<AbstractField> getFields() {
            List<AbstractField> fields = new ArrayList<>();
            for (UIComponent child : this) {
                if (child instanceof AbstractField)
                    fields.add((AbstractField) child);
            }
            return fields;
        }

        public void remove(AbstractField field) {
            this.getComponents().remove(field);
        }

        @Override
        public DataRow getCurrent() {
            return source.getCurrent();
        }

        @Override
        public boolean isReadonly() {
            return source.isReadonly();
        }

        @Override
        public void updateValue(String id, String code) {
            source.updateValue(id, code);
        }

    }

    public class UIFirstSearch extends UIComponent implements SearchSource {
        private SearchSource source;

        public UIFirstSearch(UIComponent owner) {
            super(owner);
            this.setRootLabel("div");
            // 查找最近的数据源
            UIComponent root = owner;
            while (root != null) {
                if (root instanceof SearchSource) {
                    this.source = (SearchSource) root;
                    break;
                }
                root = root.getOwner();
            }
        }

        @Override
        public DataRow getCurrent() {
            return source.getCurrent();
        }

        @Override
        public boolean isReadonly() {
            return source.isReadonly();
        }

        @Override
        public void updateValue(String id, String code) {
            source.updateValue(id, code);
        }

    }

    public final UIFirstSearch getFirstSearch() {
        return firstSearch;
    }

}
