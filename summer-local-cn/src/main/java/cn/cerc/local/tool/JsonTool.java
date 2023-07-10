package cn.cerc.local.tool;

import java.lang.reflect.Field;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import cn.cerc.db.core.ClassConfig;
import cn.cerc.db.core.Datetime;
import cn.cerc.db.core.EntityHelper;
import cn.cerc.db.core.EntityImpl;
import cn.cerc.db.core.Utils;

public class JsonTool {
    private static final Logger log = LoggerFactory.getLogger(ClassConfig.class);
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
     * 数据实体对象转MongoDB文档
     * 
     * @param <T>
     * @param entity
     * @return
     */
    public static <T extends EntityImpl> Document toDocument(T entity) {
        var doc = new Document();
        try {
            Map<String, Field> fields = EntityHelper.create(entity.getClass()).fields();
            for (String fieldCode : fields.keySet()) {
                var value = fields.get(fieldCode).get(entity);
                if (value instanceof Datetime datetime)
                    value = datetime.getTimestamp();
                doc.append(fieldCode, value);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.warn(e.getMessage());
        }
        return doc;
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
