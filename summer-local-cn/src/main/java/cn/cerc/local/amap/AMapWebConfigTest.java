package cn.cerc.local.amap;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import cn.cerc.db.redis.JedisFactory;
import redis.clients.jedis.Jedis;

public class AMapWebConfigTest {
    private static final int THREAD_COUNT = 50000;
    public static void main(String[] args) throws InterruptedException {
        Set<AMapWebConfig> set = new HashSet<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                AMapWebConfig config = AMapWebConfig.getKey();
                set.add(config);
                if (set.size() > 1) {
                    System.out.println("stop" + set.size());
                    System.exit(0);
                }
            }).start();
        }
    }
}
