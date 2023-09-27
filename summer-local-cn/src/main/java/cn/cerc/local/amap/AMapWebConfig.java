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
public class AMapWebConfig {
    private final List<String> list;
    private static final AtomicInteger counter = new AtomicInteger();
    private static volatile AMapWebConfig instance;

    private AMapWebConfig() {
        this.list = getList();
    }

    protected AMapWebConfig(List<String> list) {
        this.list = list;
    }

    private List<String> getList() {
        String value = ServerConfig.getInstance().getProperty("amap.web.svc.key");
        if (Utils.isEmpty(value))
            throw new RuntimeException("高德地图 amap.web.svc.key 尚未配置");
        return List.of(value.split(","));
    }

    protected String getNext() {
        int index = counter.getAndIncrement() % list.size();
        return list.get(index);
    }

    public static AMapWebConfig getKey() {
        if (instance == null) {
            synchronized (AMapWebConfig.class) {
                if (instance == null)
                    instance = new AMapWebConfig();
            }
        }
        return AMapWebConfig.instance;
    }

}
