package cn.cerc.local.amap;

import cn.cerc.db.core.Utils;

/**
 * 坐标系转换
 */
public class GeodeticSystem {
    /**
     * 长半轴
     */
    private static final double a = 6378245.0d;
    /**
     * 扁率
     */
    private static final double ee = 0.00669342162296594323d;

    /**
     * WGS84转GCJ02(火星坐标系)
     *
     * @param lon WGS84坐标系的经度
     * @param lat WGS84坐标系的纬度
     */
    public static double[] WGS84ToGCJ02(double lon, double lat) {
        if (isForeign(lon, lat))
            return new double[] { lon, lat };

        double dlat = transformToLat(lon - 105.0, lat - 35.0);
        double dlon = transformToLon(lon - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * Math.PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        dlon = (dlon * 180.0) / (a / sqrtMagic * Math.cos(radlat) * Math.PI);
        double mglon = lon + dlon;
        double mglat = lat + dlat;
        return new double[] { Utils.roundTo(mglon, -6), Utils.roundTo(mglat, -6) };
    }

    /**
     * GCJ02转WGS84
     * 
     * @param lon GCJ02坐标系的经度
     * @param lat GCJ02坐标系的纬度
     */
    public static double[] GCJ02ToWGS84(double lon, double lat) {
        if (isForeign(lon, lat))
            return new double[] { lon, lat };

        double dlat = transformToLat(lon - 105.0, lat - 35.0);
        double dlon = transformToLon(lon - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * Math.PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        dlon = (dlon * 180.0) / (a / sqrtMagic * Math.cos(radlat) * Math.PI);
        double mglon = lon * 2 - (lon + dlon);
        double mglat = lat * 2 - (lat + dlat);
        return new double[] { Utils.roundTo(mglon, -6), Utils.roundTo(mglat, -6) };
    }

    /**
     * 纬度计算
     *
     * @param lon 经度
     * @param lat 纬度
     */
    private static double transformToLat(double lon, double lat) {
        double ret = -100.0 + 2.0 * lon + 3.0 * lat + 0.2 * lat * lat + 0.1 * lon * lat
                + 0.2 * Math.sqrt(Math.abs(lon));
        ret += (20.0 * Math.sin(6.0 * lon * Math.PI) + 20.0 * Math.sin(2.0 * lon * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * Math.PI) + 40.0 * Math.sin(lat / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * Math.PI) + 320 * Math.sin(lat * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 经度计算
     *
     * @param lon 经度
     * @param lat 纬度
     */
    private static double transformToLon(double lon, double lat) {
        double ret = 300.0 + lon + 2.0 * lat + 0.1 * lon * lon + 0.1 * lon * lat + 0.1 * Math.sqrt(Math.abs(lon));
        ret += (20.0 * Math.sin(6.0 * lon * Math.PI) + 20.0 * Math.sin(2.0 * lon * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lon * Math.PI) + 40.0 * Math.sin(lon / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lon / 12.0 * Math.PI) + 300.0 * Math.sin(lon / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 判断是否在国内，不在国内不做偏移
     *
     * @param lon 经度
     * @param lat 纬度
     */
    private static boolean isForeign(double lon, double lat) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        else
            return lat < 0.8293 || lat > 55.8271;
    }

}
