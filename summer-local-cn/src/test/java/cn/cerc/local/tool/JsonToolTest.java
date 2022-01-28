package cn.cerc.local.tool;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import cn.cerc.local.tool.JsonTool;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonToolTest {

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