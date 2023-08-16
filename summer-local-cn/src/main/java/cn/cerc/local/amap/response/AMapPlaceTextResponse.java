package cn.cerc.local.amap.response;

import java.util.List;

/**
 * 关键字搜索返回对象
 */
public class AMapPlaceTextResponse {

    private int status;
    private String info;
    private int count;
    private Suggestion suggestion;
    private List<Poi> pois;

    /**
     * 城市建议列表
     */
    public static class Suggestion {
        private List<String> keywords;
        private List<Cities> cities;

        public static class Cities {
            private String name;
            private int num;
            private String citycode;
            private String adcode;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }
        }

        public List<String> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<String> keywords) {
            this.keywords = keywords;
        }

        public List<Cities> getCities() {
            return cities;
        }

        public void setCities(List<Cities> cities) {
            this.cities = cities;
        }
    }

    /**
     * 搜索POI信息列表
     */
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

        public static class Indoor_data {
            private String cpid;
            private String floor;
            private String truefloor;

            public String getCpid() {
                return cpid;
            }

            public void setCpid(String cpid) {
                this.cpid = cpid;
            }

            public String getFloor() {
                return floor;
            }

            public void setFloor(String floor) {
                this.floor = floor;
            }

            public String getTruefloor() {
                return truefloor;
            }

            public void setTruefloor(String truefloor) {
                this.truefloor = truefloor;
            }
        }

        public static class Biz_ext {
            private List<String> rating;
            private List<String> cost;

            public List<String> getRating() {
                return rating;
            }

            public void setRating(List<String> rating) {
                this.rating = rating;
            }

            public List<String> getCost() {
                return cost;
            }

            public void setCost(List<String> cost) {
                this.cost = cost;
            }
        }

        public static class Photos {
            private List<String> titile;
            private String url;

            public List<String> getTitile() {
                return titile;
            }

            public void setTitile(List<String> titile) {
                this.titile = titile;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getParent() {
            return parent;
        }

        public void setParent(List<String> parent) {
            this.parent = parent;
        }

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypecode() {
            return typecode;
        }

        public void setTypecode(String typecode) {
            this.typecode = typecode;
        }

        public List<String> getBiz_type() {
            return biz_type;
        }

        public void setBiz_type(List<String> biz_type) {
            this.biz_type = biz_type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<String> getDistance() {
            return distance;
        }

        public void setDistance(List<String> distance) {
            this.distance = distance;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPcode() {
            return pcode;
        }

        public void setPcode(String pcode) {
            this.pcode = pcode;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getAdname() {
            return adname;
        }

        public void setAdname(String adname) {
            this.adname = adname;
        }

        public String getEntr_location() {
            return entr_location;
        }

        public void setEntr_location(String entr_location) {
            this.entr_location = entr_location;
        }

        public String getExit_location() {
            return exit_location;
        }

        public void setExit_location(String exit_location) {
            this.exit_location = exit_location;
        }

        public String getNavi_poiid() {
            return navi_poiid;
        }

        public void setNavi_poiid(String navi_poiid) {
            this.navi_poiid = navi_poiid;
        }

        public String getGridcode() {
            return gridcode;
        }

        public void setGridcode(String gridcode) {
            this.gridcode = gridcode;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getParking_type() {
            return parking_type;
        }

        public void setParking_type(String parking_type) {
            this.parking_type = parking_type;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getIndoor_map() {
            return indoor_map;
        }

        public void setIndoor_map(String indoor_map) {
            this.indoor_map = indoor_map;
        }

        public Indoor_data getIndoor_data() {
            return indoor_data;
        }

        public void setIndoor_data(Indoor_data indoor_data) {
            this.indoor_data = indoor_data;
        }

        public String getGroupbuy_num() {
            return groupbuy_num;
        }

        public void setGroupbuy_num(String groupbuy_num) {
            this.groupbuy_num = groupbuy_num;
        }

        public String getBusiness_area() {
            return business_area;
        }

        public void setBusiness_area(String business_area) {
            this.business_area = business_area;
        }

        public String getDiscount_num() {
            return discount_num;
        }

        public void setDiscount_num(String discount_num) {
            this.discount_num = discount_num;
        }

        public Biz_ext getBiz_ext() {
            return biz_ext;
        }

        public void setBiz_ext(Biz_ext biz_ext) {
            this.biz_ext = biz_ext;
        }

        public List<Photos> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photos> photos) {
            this.photos = photos;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public List<Poi> getPois() {
        return pois;
    }

    public void setPois(List<Poi> pois) {
        this.pois = pois;
    }
}
