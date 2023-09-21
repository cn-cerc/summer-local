package cn.cerc.local.amap;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import cn.cerc.db.redis.JedisFactory;
import redis.clients.jedis.Jedis;

public class AMapWebConfigTest {
    private static final int THREAD_COUNT = 100000;
    private static final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        Set<AMapWebConfig> set = new HashSet<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    AMapWebConfig config = AMapWebConfig.getKey();
                    set.add(config);
                    if (set.size() > 1) {
                        try (Jedis redis = JedisFactory.getJedis()) {
                            redis.hset("itjun-thread", String.valueOf(config.hashCode()),
                                    String.valueOf(Thread.currentThread().getId()));
                        }
                        System.out.println("stop");
                        System.exit(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            latch.countDown();
        }
    }
}
