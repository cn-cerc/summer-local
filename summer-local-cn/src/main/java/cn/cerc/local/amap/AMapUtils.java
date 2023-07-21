package cn.cerc.local.amap;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cn.cerc.db.core.Curl;
import cn.cerc.db.core.Utils;
import cn.cerc.local.amap.response.AMapDrivingResponse;
import cn.cerc.local.amap.response.AMapGeoResponse;
import cn.cerc.local.amap.response.AMapIPResponse;
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

    /**
     * 地球半径,单位 km
     */
    private static final double EARTH_RADIUS = 6378.137;
    /**
     * 中国地理中心-陕西省泾阳县永乐镇北流村
     */
    public static final String Center_Coordinates = "108.919244,34.539972";

    /**
     * 空的经纬度字符串，用于替换掉在地址开窗时传入根据IP查询的经纬度，传入空值亦能获取当前地址作为地图的中心点
     */
    public static final String Empty_Coordinates = "";

    /**
     * 线路规划请求参数：是否返回 steps 字段内容，1为不返回，0为返回，默认为0
     */
    public static final String NO_STEPS = "1";

    /**
     * 地理编码 https://lbs.amap.com/api/webservice/guide/api/georegeo#regeo
     *
     * @param address 规则遵循：国家、省份、城市、区县、城镇、乡村、街道、门牌号码、屋邨、大厦，如：北京市朝阳区阜通东大街6号
     */
    public static AMapGeoResponse geo(String address) {
        Curl curl = new Curl();
        curl.put("key", AMapConfig.Web_Service_Key).put("address", address);
        String json = curl.doGet("http://restapi.amap.com/v3/geocode/geo");
        try {
            AMapGeoResponse response = JSON.parseObject(json, AMapGeoResponse.class);
            if ("1".equals(response.getStatus()))
                return response;
            else
                return null;
        } catch (JsonSyntaxException e) {
            log.warn("参数 {} 返回 {}", new Gson().toJson(curl.getParameters()), json, e);
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
     * 作废请使用：SvrGeocode.execute.callRemote(new PApiToken(handle),
     * DataRow.of("address_", address)) 服务获取经纬度信息
     * 
     * 返回输入地址address的经纬度信息, 格式是 经度,纬度
     * <p>
     * 输入：广东省深圳市宝安区西乡街道固戍二路鸿宇商务大厦601
     * <p>
     * 输出：113.848362,22.600957
     */
    @Deprecated
    public static String getLonLat(String address) {
        if (Utils.isEmpty(address))
            return null;

        address = address.replaceAll("[\\s\\t\\n\\r]", "").replaceAll("请选择", "").replaceAll("\\(无\\)", "").trim();
        if (Utils.isEmpty(address))
            return null;

        Curl curl = new Curl();
        curl.put("key", AMapConfig.Web_Service_Key);
        curl.put("address", address);
        String json = curl.doGet("http://restapi.amap.com/v3/geocode/geo");
        log.debug("参数 {} 返回 {}", new Gson().toJson(curl.getParameters()), json);
        if (Utils.isEmpty(json))
            return "";

        JsonNode node = JsonTool.getNode(json, "geocodes");
        if (node == null) {
            log.error("address: {}, response {}", address, json);
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

    /**
     * 传入内容规则：经度在前，纬度在后，经纬度间以“,”分割，经纬度小数点后不要超过 6 位。如果需要解析多个经纬度的话，请用"|"进行间隔，并且将 batch
     * 参数设置为 true，最多支持传入 20 对坐标点。每对点坐标之间用"|"分割。
     *
     * @param location
     */
    public static AMapRegeoResponse getAddress(String location) {
        Curl curl = new Curl();
        curl.put("key", AMapConfig.Web_Service_Key);
        curl.put("location", location);
        String json = curl.doGet("https://restapi.amap.com/v3/geocode/regeo");

        try {
            return new Gson().fromJson(json, AMapRegeoResponse.class);
        } catch (JsonSyntaxException e) {
            log.error("参数 {} 返回 {}", new Gson().toJson(curl.getParameters()), json, e);
            return null;
        }
    }

    public static String getLonLatFromIP(String ip) {
        // https://restapi.amap.com/v3/ip?ip=114.247.50.2&output=xml&key=<用户的key>

        if ("127.0.0.1".equals(ip))
            return Center_Coordinates;

        Curl curl = new Curl();
        curl.put("key", AMapConfig.Web_Service_Key);
        curl.put("ip", ip);
        String json = curl.doGet("https://restapi.amap.com/v3/ip");
        try {
            AMapIPResponse response = new Gson().fromJson(json, AMapIPResponse.class);
            return AMapUtils.getLonLat(String.join("", response.getProvince(), response.getCity()));
        } catch (JsonSyntaxException e) {
            log.error("参数 {} 返回 {}", new Gson().toJson(curl.getParameters()), json, e);
            return Center_Coordinates;
        }
    }

    /**
     * 获取从起点到终点，沿途各个途径点的线路里程（驾车线路规划）
     *
     * @param wayPoints 途经点，包括起点与终点
     * @return 路线距离（米）
     */
    public static double getDrivingDistance(List<String> wayPoints) {
        List<List<String>> totalPoints = divideList(wayPoints, 16);
        double totalDistance = 0d;
        for (List<String> list : totalPoints) {
            int lastOne = list.size() - 1;
            String origin = list.get(0);// 起点
            String destination = list.get(lastOne);// 终点
            String waypoints = String.join(";", list);
            Curl curl = new Curl();
            curl.put("key", AMapConfig.Web_Service_Key);
            curl.put("nosteps", NO_STEPS);
            curl.put("origin", origin);// 起点
            curl.put("destination", destination);// 终点
            curl.put("waypoints", waypoints);// 途经点
            String json = curl.doGet("https://restapi.amap.com/v3/direction/driving");
            AMapDrivingResponse response = JSON.parseObject(json, AMapDrivingResponse.class);
            log.debug("参数 {} 返回 {}", new Gson().toJson(curl.getParameters()), json);
            if (response.getStatus() == 1) {
                totalDistance += response.getRoute().getPaths().get(0).getDistance();
            }
        }
        return totalDistance;
    }

    /**
     * 按数量对List连续分组
     *
     * @param sourceList 原始组List
     * @param groupSize  每组数量单位
     * @return group [["0","1","2"],["2","3","4"],["4","5"]]
     */
    public static <T> List<List<T>> divideList(List<T> sourceList, int groupSize) {
        List<List<T>> list = new ArrayList<List<T>>(groupSize);
        if (sourceList == null || sourceList.size() == 0)
            return list;
        if (groupSize <= 0)
            return list;

        int listSize = sourceList.size();
        int startIndex = 0;
        while (startIndex < sourceList.size()) {
            if (startIndex > 0 && groupSize > 1)
                startIndex -= 1;
            int endIndex = Math.min(startIndex + groupSize, listSize);
            list.add(sourceList.subList(startIndex, endIndex));
            startIndex += groupSize;
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> original = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            original.add(String.valueOf(i));
        }
        int groupSize = 3;
        List<List<String>> groupList = AMapUtils.divideList(original, groupSize);
        System.out.println(groupList);
    }

}
