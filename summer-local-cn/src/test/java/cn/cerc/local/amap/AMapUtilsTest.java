package cn.cerc.local.amap;

import cn.cerc.local.amap.response.AMapGeoResponse;
import cn.cerc.local.amap.response.AMapGeoResponse.Geocodes;
import cn.cerc.local.amap.response.AMapRegeoResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class AMapUtilsTest {

    private static String example_address = "广东省深圳市宝安区西乡街道固戍二路鸿宇商务大厦601\n";

    private String address;

    @Before
    public void init() {
        this.address = example_address.replaceAll("[\\s\\t\\n\\r]", "").trim();
        log.info("格式化后地址 {}", this.address);
    }

    @Test
    public void test_geo() {
        AMapGeoResponse geo = AMapUtils.geo(address);
        Geocodes geocode = geo.getGeocodes().get(0);
        log.info("{} 正向地理编码 {}", address, geocode.getLocation());
    }

    @Test
    public void test_getDistance() {
        // 深圳北站
        double longitude1 = 114.0295d;
        double latitude1 = 22.609875d;

        // 公司地址
        double longitude2 = 113.848362d;
        double latitude2 = 22.600957d;

        double distance = AMapUtils.getDistance(longitude1, latitude1, longitude2, latitude2);
        System.out.println(distance);
    }

    @Test
    public void test_getLonLat() {
        log.info("{} 转换后得到坐标 {}", address, AMapUtils.getLonLat(address));
    }

    @Test
    public void test_getAddress() {
        String location = "114.057868,22.543099";
        AMapRegeoResponse regeo = AMapUtils.getAddress(location);
        log.info("{}", regeo);
        AMapRegeoResponse.Regeocode.AddressComponent item = regeo.getRegeocode().getAddressComponent();
        log.info("{} 逆地理编码地址 {} {} {}", location, item.getProvince(), item.getCity(), item.getDistrict());
    }

    @Test
    public void test_getUserLocation() {
        String ip = "113.87.154.66";
        log.info("ip {} 转坐标 {}", ip, AMapUtils.getLonLatFromIP(ip));
    }

    public static void main(String[] args) {
        String a = "113.848362";
        System.out.println(a.length());

        String b = "22.600957";
        System.out.println(b.length());
    }

}
