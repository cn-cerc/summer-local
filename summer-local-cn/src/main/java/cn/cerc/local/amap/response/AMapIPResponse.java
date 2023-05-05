package cn.cerc.local.amap.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AMapIPResponse {

    private String status;
    private String info;
    private String infocode;
    private String province;
    private Object city;
    private Object adcode;
    private Object rectangle;

    public String getCity() {
        return city instanceof String ? (String) city : "";
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode instanceof String ? (String) adcode : "";
    }

    public void setAdcode(Object adcode) {
        this.adcode = adcode;
    }

    public String getRectangle() {
        return rectangle instanceof String ? (String) rectangle : "";
    }

    public void setRectangle(Object rectangle) {
        this.rectangle = rectangle;
    }
}
