package cn.cerc.local.amap.response;

import java.util.List;

/**
 * 正向地理编码返回对象
 */
public class AMapGeoResponse {

    private String status;
    private String info;
    private String infocode;
    private String count;
    private List<Geocodes> geocodes;

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

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public List<String> getTownship() {
            return township;
        }

        public void setTownship(List<String> township) {
            this.township = township;
        }

        public Neighborhood getNeighborhood() {
            return neighborhood;
        }

        public void setNeighborhood(Neighborhood neighborhood) {
            this.neighborhood = neighborhood;
        }

        public Building getBuilding() {
            return building;
        }

        public void setBuilding(Building building) {
            this.building = building;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public List<String> getStreet() {
            return street;
        }

        public void setStreet(List<String> street) {
            this.street = street;
        }

        public List<String> getNumber() {
            return number;
        }

        public void setNumber(List<String> number) {
            this.number = number;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }

    public static class Neighborhood {
        private List<String> name;
        private List<String> type;

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }
    }

    public static class Building {
        private List<String> name;
        private List<String> type;

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Geocodes> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(List<Geocodes> geocodes) {
        this.geocodes = geocodes;
    }
}