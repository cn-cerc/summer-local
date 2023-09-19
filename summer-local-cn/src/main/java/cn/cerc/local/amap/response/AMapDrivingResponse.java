package cn.cerc.local.amap.response;

import java.util.List;

/**
 * 高德驾车路径规划返回结果
 */
public class AMapDrivingResponse {
    private int status;
    private String info;
    private String count;
    private Route route;

    public static class Route {
        private String origin;
        private String destination;
        private List<Path> paths;

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public List<Path> getPaths() {
            return paths;
        }

        public void setPaths(List<Path> paths) {
            this.paths = paths;
        }

    }

    public static class Path {
        private double distance;// 距离，米
        private String duration;
        private String strategy;
        private String tolls;
        private String restriction;
        private String traffic_lights;
        private String toll_distance;
        private List<Step> steps;

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }

        public String getTolls() {
            return tolls;
        }

        public void setTolls(String tolls) {
            this.tolls = tolls;
        }

        public String getRestriction() {
            return restriction;
        }

        public void setRestriction(String restriction) {
            this.restriction = restriction;
        }

        public String getTraffic_lights() {
            return traffic_lights;
        }

        public void setTraffic_lights(String traffic_lights) {
            this.traffic_lights = traffic_lights;
        }

        public String getToll_distance() {
            return toll_distance;
        }

        public void setToll_distance(String toll_distance) {
            this.toll_distance = toll_distance;
        }

        public List<Step> getSteps() {
            return steps;
        }

        public void setSteps(List<Step> steps) {
            this.steps = steps;
        }

    }

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

        public String getInstruction() {
            return instruction;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getTolls() {
            return tolls;
        }

        public void setTolls(String tolls) {
            this.tolls = tolls;
        }

        public Object getToll_distance() {
            return toll_distance;
        }

        public void setToll_distance(Object toll_distance) {
            this.toll_distance = toll_distance;
        }

        public Object getToll_road() {
            return toll_road;
        }

        public void setToll_road(Object toll_road) {
            this.toll_road = toll_road;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getPolyline() {
            return polyline;
        }

        public void setPolyline(String polyline) {
            this.polyline = polyline;
        }

        public Object getAction() {
            return action;
        }

        public void setAction(Object action) {
            this.action = action;
        }

        public Object getAssistant_action() {
            return assistant_action;
        }

        public void setAssistant_action(Object assistant_action) {
            this.assistant_action = assistant_action;
        }

        public List<Tmc> getTmcs() {
            return tmcs;
        }

        public void setTmcs(List<Tmc> tmcs) {
            this.tmcs = tmcs;
        }

        public List<City> getCitys() {
            return citys;
        }

        public void setCitys(List<City> citys) {
            this.citys = citys;
        }

    }

    public static class Tmc {
        private Object lcode;
        private String distance;
        private String status;
        private String polyline;

        public Object getLcode() {
            return lcode;
        }

        public void setLcode(Object lcode) {
            this.lcode = lcode;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPolyline() {
            return polyline;
        }

        public void setPolyline(String polyline) {
            this.polyline = polyline;
        }

    }

    public static class City {
        private String name;
        private String citycode;
        private String adcode;
        private List<District> districts;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public List<District> getDistricts() {
            return districts;
        }

        public void setDistricts(List<District> districts) {
            this.districts = districts;
        }

    }

    public static class District {
        private String name;
        private String adcode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
