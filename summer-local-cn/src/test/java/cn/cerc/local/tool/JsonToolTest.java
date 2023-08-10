package cn.cerc.local.tool;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonToolTest {
    private static final Logger log = LoggerFactory.getLogger(JsonToolTest.class);

    @Test
    public void test_json() {
        Map<String, String> items = new LinkedHashMap<>();
        items.put("1", "a");
        items.put("2", "b");
        items.put("3", "c");
        String value = JsonTool.transferToJson(items);
        log.info(value);

        TypeFactory typeFactory = new ObjectMapper().getTypeFactory();
        MapType mapType = typeFactory.constructMapType(LinkedHashMap.class, String.class, String.class);

        LinkedHashMap<String, String> map = JsonTool.transferToObj(value, mapType);
        map.forEach((k, v) -> log.info("{} {}", k, v));
    }

}