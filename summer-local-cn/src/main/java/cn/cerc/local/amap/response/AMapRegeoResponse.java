package cn.cerc.local.amap.response;

import java.util.Collection;
import java.util.List;

import cn.cerc.db.core.Utils;

/**
 * 逆地理编码返回对象
 */
public class AMapRegeoResponse {
    private String status;
    private Regeocode regeocode;
    private String info;
    private String infocode;

    public static class Regeocode {
        private AddressComponent addressComponent;
        private Object formatted_address;

        public AddressComponent getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponent addressComponent) {
            this.addressComponent = addressComponent;
        }

        public String getFormatted_address() {
            return getStringValue(formatted_address);
        }

        public void setFormatted_address(Object formatted_address) {
            this.formatted_address = formatted_address;
        }

    }

    public static class AddressComponent {
        private Object city;
        private Object province;
        private Object adcode;
        private Object district;
        private Object towncode;
        private StreetNumber streetNumber;
        private Object country;
        private Object township;
        private List<Object> businessAreas;
        private Building building;
        private Neighborhood neighborhood;
        private Object citycode;

        public String getCity() {
            return getStringValue(city);
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public String getProvince() {
            return getStringValue(province);
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public String getAdcode() {
            return getStringValue(adcode);
        }

        public void setAdcode(Object adcode) {
            this.adcode = adcode;
        }

        public String getDistrict() {
            return getStringValue(district);
        }

        public void setDistrict(Object district) {
            this.district = district;
        }

        public String getTowncode() {
            return getStringValue(towncode);
        }

        public void setTowncode(Object towncode) {
            this.towncode = towncode;
        }

        public StreetNumber getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(StreetNumber streetNumber) {
            this.streetNumber = streetNumber;
        }

        public String getCountry() {
            return getStringValue(country);
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public String getTownship() {
            return getStringValue(township);
        }

        public void setTownship(Object township) {
            this.township = township;
        }

        public List<Object> getBusinessAreas() {
            return businessAreas;
        }

        public void setBusinessAreas(List<Object> businessAreas) {
            this.businessAreas = businessAreas;
        }

        public Building getBuilding() {
            return building;
        }

        public void setBuilding(Building building) {
            this.building = building;
        }

        public Neighborhood getNeighborhood() {
            return neighborhood;
        }

        public void setNeighborhood(Neighborhood neighborhood) {
            this.neighborhood = neighborhood;
        }

        public String getCitycode() {
            return getStringValue(citycode);
        }

        public void setCitycode(Object citycode) {
            this.citycode = citycode;
        }

    }

    /**
     * 街道信息
     */
    public static class StreetNumber {
        private Object number;
        private Object location;
        private Object direction;
        private Object distance;
        private Object street;

        public Object getNumber() {
            return number;
        }

        public void setNumber(Object number) {
            this.number = number;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public Object getDirection() {
            return direction;
        }

        public void setDirection(Object direction) {
            this.direction = direction;
        }

        public Object getDistance() {
            return distance;
        }

        public void setDistance(Object distance) {
            this.distance = distance;
        }

        public Object getStreet() {
            return street;
        }

        public void setStreet(Object street) {
            this.street = street;
        }

    }

    public static class Building {
        private Object name;
        private Object type;

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

    }

    public static class Neighborhood {
        private Object name;
        private Object type;

        public String getName() {
            return getStringValue(name);
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getType() {
            return getStringValue(type);
        }

        public void setType(Object type) {
            this.type = type;
        }

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Regeocode getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(Regeocode regeocode) {
        this.regeocode = regeocode;
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

    private static String getStringValue(Object value) {
        if (value == null)
            return "";
        // 高德接口返回值为空时为 [] 会被解析成list
        if (value instanceof Collection<?> collection && Utils.isEmpty(collection))
            return "";
        if (value instanceof Object[] arr && Utils.isEmpty(arr))
            return "";
        return String.valueOf(value);
    }

}
