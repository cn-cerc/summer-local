package cn.cerc.local.amap.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 高德驾车路径规划返回结果
 */
@Getter
@Setter
public class AMapDrivingResponse {
    private int status;
    private String info;
    private String count;
    private Route route;

    @Getter
    @Setter
    public static class Route {
        private String origin;
        private String destination;
        private List<Path> paths;
    }

    @Getter
    @Setter
    public static class Path {
        private double distance;// 距离，米
        private String duration;
        private String strategy;
        private String tolls;
        private String restriction;
        private String traffic_lights;
        private String toll_distance;
        private List<Step> steps;
    }

    @Getter
    @Setter
    public static class Step {
        private String instruction;
        private String orientation;
        private String distance;
        private String tolls;
        private String toll_distance;
        private String[] toll_road;
        private String duration;
        private String polyline;
        private String action;
        private String[] assistant_action;
    }

}
