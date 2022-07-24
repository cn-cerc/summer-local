package cn.cerc.local.amap;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import cn.cerc.db.core.Curl;
import cn.cerc.db.core.Utils;
import cn.cerc.local.amap.response.AMapGeoResponse;
import cn.cerc.local.amap.response.AMapRegeoResponse;
import cn.cerc.local.tool.JsonTool;
import lombok.extern.slf4j.Slf4j;

/**
 * 高德地图工具类
 * <p>
 * 在线转换网址 https://lbs.amap.com/tools/picker
 */
@Slf4j
public class AMapUtils {

    private static final String API_URL = "https://restapi.amap.com/v3";
    /**
     * 地球半径,单位 km
     */
    private static final double EARTH_RADIUS = 6378.137;

    /**
     * 地理编码 https://lbs.amap.com/api/webservice/guide/api/georegeo#regeo
     * 
     * @param address 规则遵循：国家、省份、城市、区县、城镇、乡村、街道、门牌号码、屋邨、大厦，如：北京市朝阳区阜通东大街6号
     */
    public static AMapGeoResponse geo(String address) {
        Curl curl = new Curl();
        curl.put("key", AMapConfig.Web_Service_Key).put("address", address);
        String json = curl.doGet(API_URL + "/geocode/geo");
        try {
            AMapGeoResponse response = JSON.parseObject(json, AMapGeoResponse.class);
            if ("1".equals(response.getStatus())) {
                log.warn("高德接口请求成功 API:{} 参数:{} 返回:{}", API_URL + "/geocode/geo",
                        new Gson().toJson(curl.getParameters()), json);
                return response;
            } else {
                log.warn("高德接口请求失败 API:{} 参数:{} 返回:{}", API_URL + "/geocode/geo",
                        new Gson().toJson(curl.getParameters()), json);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("高德接口请求失败 API:{} 参数:{} 返回:{}", API_URL + "/geocode/geo", new Gson().toJson(curl.getParameters()),
                    json);
            return null;
        }
    }

    /**
     * 根据经纬度，计算两点间的距离
     *
     * @param longitude1 第一个点的经度
     * @param latitude1  第一个点的纬度
     * @param longitude2 第二个点的经度
     * @param latitude2  第二个点的纬度
     * @return 返回距离 单位米
     */
    public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        // 纬度
        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);

        // 经度
        double lng1 = Math.toRadians(longitude1);
        double lng2 = Math.toRadians(longitude2);

        // 纬度之差
        double a = lat1 - lat2;
        // 经度之差
        double b = lng1 - lng2;

        // 计算两点距离的公式
        double s = 2 * Math.asin(Math
                .sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(b / 2), 2)));

        // 弧长乘地球半径, 返回单位: 千米
        s = s * EARTH_RADIUS;
        return s * 1000;
    }

    /**
     * 返回输入地址address的经纬度信息, 格式是 经度,纬度
     * 
     * 输入：广东省深圳市宝安区西乡街道固戍二路鸿宇商务大厦601
     * 
     * 输出：113.848362,22.600957
     */
    public static String getLonLat(String address) {
        if (Utils.isEmpty(address)) {
            return null;
        }
        address = address.replaceAll("[\\s\\t\\n\\r]", "").trim();
        if (Utils.isEmpty(address)) {
            return null;
        }

        Curl curl = new Curl();
        curl.put("key", AMapConfig.Web_Service_Key);
        curl.put("address", address);
        String response = curl.doGet("http://restapi.amap.com/v3/geocode/geo");
        log.debug(response);
        if (Utils.isEmpty(response)) {
            return "";
        }

        JsonNode node = JsonTool.getNode(response, "geocodes");
        if (node == null) {
            log.error("address: {}, response {}", address, response);
            return null;
        }
        if (node.isArray()) {
            JsonNode index = node.get(0);
            if (index == null) {
                return null;
            }
            return index.get("location").asText();
        }
        return "";
    }

    /*
     * https://lbs.amap.com/service/api/restapi?location=117.007792,31.275725+&
     * poitype=&radius=1000&extensions=base&batch=false&roadlevel=1
     */

    /**
     * 传入内容规则：经度在前，纬度在后，经纬度间以“,”分割，经纬度小数点后不要超过 6 位。如果需要解析多个经纬度的话，请用"|"进行间隔，并且将 batch
     * 参数设置为 true，最多支持传入 20 对坐标点。每对点坐标之间用"|"分割。
     * 
     * @param location
     * @return
     */
    public static AMapRegeoResponse getAddress(String location) {
        Curl curl = new Curl();
        curl.put("key", AMapConfig.Web_Service_Key);
        curl.put("location", location);
        String response = curl.doGet("https://restapi.amap.com/v3/geocode/regeo");
        System.out.println(response);
        AMapRegeoResponse regeo = new Gson().fromJson(response, AMapRegeoResponse.class);
        return regeo;
    }

}
