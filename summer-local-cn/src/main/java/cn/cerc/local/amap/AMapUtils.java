package cn.cerc.local.amap;

import com.fasterxml.jackson.databind.JsonNode;

import cn.cerc.db.core.Curl;
import cn.cerc.db.core.Utils;
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
     * 返回输入地址address的经纬度信息, 格式是 经度,纬度
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

    public static void main(String[] args) {
        String address = "广东省深圳市宝安区西乡街道固戍二路鸿宇商务大厦601\n";
        address = address.replaceAll("[\\s\\t\\n\\r]", "").trim();
        log.info(address);
        log.info(AMapUtils.getLonLat(address));
    }

}
