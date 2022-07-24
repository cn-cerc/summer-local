package cn.cerc.local.amap.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 正向地理编码返回对象
 */
@Getter
@Setter
public class AMapGeoResponse {

    private String status;
    private String info;
    private String infocode;
    private String count;
    private List<Geocodes> geocodes;

    @Getter
    @Setter
    public static class Geocodes {
        private String formatted_address;
        private String country;
        private String province;
        private String citycode;
        private String city;
        private String district;
        private List<String> township;
        private Neighborhood neighborhood;
        private Building building;
        private String adcode;
        private List<String> street;
        private List<String> number;
        private String location;
        private String level;
    }

    @Getter
    @Setter
    public static class Neighborhood {
        private List<String> name;
        private List<String> type;
    }

    @Getter
    @Setter
    public static class Building {
        private List<String> name;
        private List<String> type;
    }

}