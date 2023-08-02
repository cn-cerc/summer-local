package cn.cerc.local.amap;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class AMapWebConfigTest {

    @Test
    public void test_getKey() {
        AMapWebConfig config = new AMapWebConfig(List.of("a", "b", "c"));
        assertEquals("a", config.getNext());
        assertEquals("b", config.getNext());
        assertEquals("c", config.getNext());

        assertEquals("a", config.getNext());
        assertEquals("b", config.getNext());
        assertEquals("c", config.getNext());
    }

}
