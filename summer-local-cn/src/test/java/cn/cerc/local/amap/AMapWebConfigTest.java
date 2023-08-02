package cn.cerc.local.amap;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class AMapWebConfigTest {

    @Test
    public void test_getKey() {
        AMapWebConfig config = new AMapWebConfig(List.of("a", "b", "c"));
        assertEquals("a", config.getKey());
        assertEquals("b", config.getKey());
        assertEquals("c", config.getKey());

        assertEquals("a", config.getKey());
        assertEquals("b", config.getKey());
        assertEquals("c", config.getKey());
    }

}
