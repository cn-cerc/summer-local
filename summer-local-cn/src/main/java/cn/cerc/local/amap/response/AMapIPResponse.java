package cn.cerc.local.amap.response;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(Object city) {
        this.city = city;
    }

}
