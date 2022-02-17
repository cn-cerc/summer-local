package cn.cerc.local.alipay;

import cn.cerc.db.core.ServerConfig;
import cn.cerc.db.core.Utils;

/**
 * 支付宝支付固定参数常量
 */
public class AlipayConfig {

    /**
     * UTF-8字符集
     **/
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * JSON 应格式
     **/
    public static final String FORMAT_JSON = "json";

    /**
     * sha256WithRsa 算法请求类型
     **/
    public static final String SIGN_TYPE_RSA2 = "RSA2";

    /**
     * 商户AppID
     **/
    public static final String APP_ID = ServerConfig.getInstance().getProperty("alipay.appId");

    /**
     * 商户收款ID
     **/
    public static final String SELLER_ID = ServerConfig.getInstance().getProperty("seller_id");

    /**
     * 请求网关地址
     **/
    public static final String URL = "https://openapi.alipay.com/gateway.do";

    /**
     * 应用私钥 pkcs8格式
     **/
    public static final String APP_PRIVATE_KEY = ServerConfig.getInstance().getProperty("alipay.privateKey");

    /**
     * 支付宝公钥
     **/
    public static final String ALIPAY_PUBLIC_KEY = ServerConfig.getInstance().getProperty("alipay.alipayPublicKey");

    /**
     * 创建支付订单号
     */
    public static String getOrderNo(String corpNo) {
        return corpNo + System.currentTimeMillis() + Utils.random(1000);
    }

}
