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
            private List<Object> businessAreas;
            private Building building;
            private Neighborhood neighborhood;
            private String citycode;

            /**
             * 街道信息
             */
            @Setter
            @Getter
            public static class StreetNumber {
                private Object number;
                private Object location;
                private Object direction;
                private Object distance;
                private Object street;
            }

            @Setter
            @Getter
            public static class Building {
                private Object name;
                private Object type;
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
