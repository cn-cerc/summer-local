package cn.cerc.local.amap;

import cn.cerc.db.core.ServerConfig;

/**
 * 高德地图配置文件
 * <p>
 * 接口文档：https://lbs.amap.com/api/jsapi-v2/summary
 */
public class AMapConfig {

    /**
     * Web服务key，用于坐标获取，静态地图等服务
     */
    public static final String Web_Service_Key = ServerConfig.getInstance().getProperty("amap.web.svc.key");
    /**
     * Web服务密钥，用于坐标获取，静态地图等服务
     */
    public static final String Web_Service_Secret = ServerConfig.getInstance().getProperty("amap.web.svc.secret");

    /**
     * Web端(js-api)应用key
     */
    public static final String Web_Api_Key = ServerConfig.getInstance().getProperty("amap.web.api.key");
    /**
     * Web端(js-api)应用安全密钥
     */
    public static final String Web_Api_Secret = ServerConfig.getInstance().getProperty("amap.web.api.secret");

    /**
     * 中国地理中心-陕西省泾阳县永乐镇北流村
     */
    public static final String Center_Coordinates = "108.919244,34.539972";

    /**
     * 地图 JSAPI 版本
     */
    public static final String Version = "1.4.15";

}
