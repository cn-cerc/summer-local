package cn.cerc.local.amap;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import cn.cerc.db.core.ServerConfig;
import cn.cerc.db.core.Utils;

/**
 * 高德地图 Web服务key<br>
 * 
 * 用于坐标获取，静态地图等服务
 */
public enum AMapWebConfig {
    INSTANCE;

    private final List<String> list;
    private static final AtomicInteger counter = new AtomicInteger();

    private AMapWebConfig() {
        String value = ServerConfig.getInstance().getProperty("amap.web.svc.key");
        if (Utils.isEmpty(value))
            throw new RuntimeException("高德地图 amap.web.svc.key 尚未配置");
        this.list = List.of(value.split(","));
    }

    private String getNext() {
        int index = counter.getAndIncrement() % list.size();
        return list.get(index);
    }

    public static String getKey() {
        return AMapWebConfig.INSTANCE.getNext();
    }

}
