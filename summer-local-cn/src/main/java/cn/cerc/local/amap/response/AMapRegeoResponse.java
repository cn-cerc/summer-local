package cn.cerc.local.amap.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 逆地理编码返回对象
 */
@Setter
@Getter
public class AMapRegeoResponse {

    private String status;
    private Regeocode regeocode;
    private String info;
    private String infocode;

    @Setter
    @Getter
    public static class Regeocode {
        private AddressComponent addressComponent;
        private String formattedAddress;
    }

    @Setter
    @Getter
    public static class AddressComponent {
        private String city;
        private String province;
        private String adcode;
        private String district;
        private String towncode;
        private StreetNumber streetNumber;
        private String country;
        private String township;
        private List<List<?>> businessAreas;
        private Building building;
        private Neighborhood neighborhood;
        private String citycode;
    }

    @Setter
    @Getter
    public static class StreetNumber {
        private List<String> number;
        private List<String> direction;
        private List<String> distance;
        private List<String> street;
    }

    @Setter
    @Getter
    public static class Building {
        private List<String> name;
        private List<String> type;
    }

    @Setter
    @Getter
    public static class Neighborhood {
        private List<String> name;
        private List<String> type;
    }

}
