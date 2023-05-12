package cn.cerc.local.amap.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AMapTruckDrivingResponse {
    private int errcode;
    private String errmsg;
    private String errdetail;
    private Data data;

    @Getter
    @Setter
    public static class Data {
        private String count;
        private Route route;
    }

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
        private Object toll_distance;
        private Object toll_road;
        private String duration;
        private String polyline;
        private Object action;
        private Object assistant_action;
        private List<Tmc> tmcs;
        private List<City> citys;
    }

    @Getter
    @Setter
    public static class Tmc {
        private Object lcode;
        private String distance;
        private String status;
        private String polyline;
    }

    @Getter
    @Setter
    public static class City {
        private String name;
        private String citycode;
        private String adcode;
        private List<District> districts;
    }

    @Getter
    @Setter
    public static class District {
        private String name;
        private String adcode;
    }

}