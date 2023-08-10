package cn.cerc.local.amap.response;

import java.util.List;

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
        private String formatted_address;

        public static class AddressComponent {
            private String city;
            private String province;
            private String adcode;
            private String district;
            private Object towncode;
            private StreetNumber streetNumber;
            private String country;
            private Object township;
            private List<Object> businessAreas;
            private Building building;
            private Neighborhood neighborhood;
            private String citycode;

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
                private List<?> name;
                private List<?> type;

                public List<?> getName() {
                    return name;
                }

                public void setName(List<?> name) {
                    this.name = name;
                }

                public List<?> getType() {
                    return type;
                }

                public void setType(List<?> type) {
                    this.type = type;
                }
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public Object getTowncode() {
                return towncode;
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
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public Object getTownship() {
                return township;
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
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }
        }

        public AddressComponent getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponent addressComponent) {
            this.addressComponent = addressComponent;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
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
}
