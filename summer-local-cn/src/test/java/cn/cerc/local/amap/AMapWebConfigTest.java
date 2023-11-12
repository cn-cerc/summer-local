package cn.cerc.local.amap;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cn.cerc.db.core.ServerConfig;

public class AMapWebConfigTest {

    @Before
    public void setup() {
        ServerConfig.getInstance().setProperty("amap.web.svc.key", "a,b,c");
    }

    @Test
    public void test_getKey() {
        assertEquals("a", AMapWebConfig.getKey());
        assertEquals("b", AMapWebConfig.getKey());
        assertEquals("c", AMapWebConfig.getKey());

        assertEquals("a", AMapWebConfig.getKey());
        assertEquals("b", AMapWebConfig.getKey());
        assertEquals("c", AMapWebConfig.getKey());

        assertEquals("a", AMapWebConfig.getKey());
    }

}
