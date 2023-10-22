package cn.cerc.local.amap;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class GeodeticSystemTest {

    @Test
    public void test_1() {
        double[] value = GeodeticSystem.WGS84ToGCJ02(118.010632, 26.635384);
        assertArrayEquals(new double[] { 118.015639, 26.632035 }, value, 0.0);
    }

}