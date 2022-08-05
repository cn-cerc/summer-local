package cn.cerc.local.amap.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
        private String formatted_address;

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

            @Setter
            @Getter
            public static class StreetNumber {
                private String number;
                private String location;
                private String direction;
                private String distance;
                private String street;
            }

            @Setter
            @Getter
            public static class Building {
                private String name;
                private String type;
            }

            @Setter
            @Getter
            public static class Neighborhood {
                private List<?> name;
                private List<?> type;
            }
        }
    }
}
