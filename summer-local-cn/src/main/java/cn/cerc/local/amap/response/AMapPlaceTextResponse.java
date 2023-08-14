package cn.cerc.local.amap.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 关键字搜索返回对象
 */
@Setter
@Getter
public class AMapPlaceTextResponse {

    private int status;
    private String info;
    private int count;
    private Suggestion suggestion;
    private List<Poi> pois;

    /**
     * 城市建议列表
     */
    @Setter
    @Getter
    public static class Suggestion {
        private List<String> keywords;
        private List<Cities> cities;

        @Setter
        @Getter
        public static class Cities {
            private String name;
            private int num;
            private String citycode;
            private String adcode;
        }
    }

    /**
     * 搜索POI信息列表
     */
    @Setter
    @Getter
    public static class Poi {
        private String id;
        private List<String> parent;
        private List<String> name;
        private String type;
        private String typecode;
        private List<String> biz_type;
        private String address;
        private String location;
        private List<String> distance;
        private String tel;
        private String postcode;
        private String website;
        private String email;
        private String pcode;
        private String pname;
        private String citycode;
        private String cityname;
        private String adcode;
        private String adname;
        private String entr_location;
        private String exit_location;
        private String navi_poiid;
        private String gridcode;
        private String alias;
        private String parking_type;
        private String tag;
        private String indoor_map;
        private Indoor_data indoor_data;
        private String groupbuy_num;
        private String business_area;
        private String discount_num;
        private Biz_ext biz_ext;
        private List<Photos> photos;

        @Setter
        @Getter
        public static class Indoor_data {
            private String cpid;
            private String floor;
            private String truefloor;
        }

        @Setter
        @Getter
        public static class Biz_ext {
            private List<String> rating;
            private List<String> cost;
        }

        @Setter
        @Getter
        public static class Photos {
            private List<String> titile;
            private String url;
        }
    }

}
