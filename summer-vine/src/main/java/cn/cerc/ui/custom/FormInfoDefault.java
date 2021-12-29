package cn.cerc.ui.custom;

import org.springframework.stereotype.Component;

import cn.cerc.db.core.Utils;
import cn.cerc.mis.core.IForm;
import cn.cerc.mis.language.R;
import cn.cerc.ui.menu.MenuList;
import cn.cerc.ui.page.IFormInfo;

@Component
public class FormInfoDefault implements IFormInfo {

    @Override
    public String getFormCaption(IForm form, String formId, String defaultValue) {
        //FIXME 不得对函数使用R.asString，此处需要改进! ZhangGong 2021/3/16
        if (!Utils.isEmpty(form.getName())) {
            return R.asString(form, form.getName());
        } else {
            return R.asString(form, MenuList.create(form).getName(formId));
        }
        
    }

}
