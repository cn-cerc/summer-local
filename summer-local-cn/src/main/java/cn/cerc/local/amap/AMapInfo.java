package cn.cerc.local.amap;

import lombok.Getter;
import lombok.Setter;

/**
 * 热点分布图 信息
 */
@Getter
@Setter
public class AMapInfo {
    private String longitude;
    private String latitude;
    private String code;
    private String name;
    private String contact;
    private String mobile;
    private String address;
}
