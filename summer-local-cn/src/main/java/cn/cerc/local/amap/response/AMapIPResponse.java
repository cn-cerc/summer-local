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
    private String city;
    private String adcode;
    private String rectangle;

}
