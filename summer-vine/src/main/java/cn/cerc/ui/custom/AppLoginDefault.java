package cn.cerc.ui.custom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.cerc.core.Utils;
import cn.cerc.mis.core.AppClient;
import cn.cerc.mis.core.Application;
import cn.cerc.mis.core.IAppLogin;
import cn.cerc.mis.core.IForm;
import cn.cerc.mis.core.IUserLoginCheck;
import cn.cerc.ui.page.JspPage;

@Component
public class AppLoginDefault implements IAppLogin {
    // 注意：此处应该使用SummerMVC.ID，别改为SummerUI.ID
    private static final Logger log = LoggerFactory.getLogger(AppLoginDefault.class);

    private static final String POST = "POST";

    /**
     * 手机客户端
     */
    private static final List<String> MOBILE_CLIENTS = new ArrayList<>();
    static {
        MOBILE_CLIENTS.add("/AppleWebKit.*Mobile.*/".toLowerCase());
        MOBILE_CLIENTS.add("Mobile".toLowerCase());
        MOBILE_CLIENTS.add("Android".toLowerCase());
        MOBILE_CLIENTS.add("Adr".toLowerCase());
        MOBILE_CLIENTS.add("iPhone".toLowerCase());
        MOBILE_CLIENTS.add("iPad".toLowerCase());
        MOBILE_CLIENTS.add("MicroMessenger".toLowerCase());
        MOBILE_CLIENTS.add("qq".toLowerCase());
        MOBILE_CLIENTS.add("alipayclient".toLowerCase());
    }

    @Override
    public String getLoginView(IForm form) throws IOException, ServletException {
        JspPage jspPage = new JspPage();

        HttpServletRequest request = form.getRequest();
        String deviceId = form.getClient().getId();
        String clientip = AppClient.getClientIP(request);
        String language = form.getClient().getLanguage();

        if (needToInstall(request, deviceId)) {
            return "redirect:install?device=phone";
        }

        jspPage.setOrigin(form);
        jspPage.setJspFile(Application.getConfig().getJspLoginFile());
        jspPage.add("homePage", Application.getConfig().getWelcomePage());
        jspPage.add("needVerify", "false");

        // 若页面有传递用户帐号，则强制重新校验
        String userCode = request.getParameter("login_usr");
        String password = request.getParameter("login_pwd");
        if (Utils.isEmpty(userCode) || Utils.isEmpty(password)) {
            if (POST.equals(request.getMethod())) {
                request.getSession().setAttribute("loginMsg", "请输入正确的帐号和密码");
            } else {
                request.getSession().setAttribute("loginMsg", "");
            }
            return jspPage.execute();
        }

        request.setAttribute("userCode", userCode);
        request.setAttribute("password", password);
        request.setAttribute("needVerify", "false");

        IUserLoginCheck loginCheck = Application.getBean(form, IUserLoginCheck.class);

        // 如长度大于10表示用手机号码登入
        if (userCode.length() > 10) {
            String oldCode = userCode;
            try {
                userCode = loginCheck.getUserCode(oldCode);
                log.debug(String.format("将手机号 %s 转化成帐号 %s", oldCode, userCode));
            } catch (Exception e) {
                // 手机号获取帐号失败
                log.debug(String.format("将手机号 %s 转化成帐号失败", oldCode));
                request.getSession().setAttribute("loginMsg", e.getMessage());
                return jspPage.execute();
            }
        }

        // 进行用户名、密码认证
        String token = loginCheck.getToken(userCode, password, deviceId, clientip, language);
        if (!Utils.isEmpty(token)) {
            ((AppClient) form.getClient()).setToken(token);
            request.getSession().setAttribute("loginMsg", "");
            request.getSession().setAttribute("mobile", "");
            return null;
        } else {
            request.getSession().setAttribute("loginMsg", loginCheck.getMessage());
            return jspPage.execute();
        }
    }

    public boolean needToInstall(HttpServletRequest request, String deviceId) {
        String browser = request.getHeader("User-Agent").toLowerCase();
        log.debug(browser);
        if (browser.contains("/apicloud/i")) {
            return false;
        }
        boolean isPhone = MOBILE_CLIENTS.stream().anyMatch(browser::contains);
        if (isPhone) {
            if (Utils.isEmpty(deviceId) || Application.WebClient.equals(deviceId)) {
                return true;
            }
        }
        return false;
    }

}
