package cn.cerc.local.tool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import cn.cerc.db.core.Utils;

public class JsonTool {

    private static final ObjectMapper mapper = createObjectMapper();

    private JsonTool() {
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.INDENT_OUTPUT, false)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    /**
     * 实体对象转JSON数据
     */
    public static <T> String transferToJson(T clazz) {
        try {
            return mapper.writeValueAsString(clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json 数据转实体对象
     */
    public static <T> T transferToObj(String json, Class<T> clazz) {
        T value = null;
        if (!Utils.isEmpty(json)) {
            try {
                value = mapper.readValue(json, clazz);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * json 数据转泛型
     */
    public static <T> T transferToObj(String json, JavaType valueType) {
        if (json == null || json.length() == 0 || valueType == null) {
            return null;
        }
        try {
            return mapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取json字符串的节点信息
     * 
     * @param json      文本内容
     * @param fieldName 节点名称
     */
    public static JsonNode getNode(String json, String fieldName) {
        if (Utils.isEmpty(json)) {
            return null;
        }

        try {
            return mapper.readTree(json).get(fieldName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
