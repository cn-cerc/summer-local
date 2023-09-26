package cn.cerc.local.amap;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class AMapWebConfigTest {
    private static final int THREAD_COUNT = 50000;
    private static final CountDownLatch startLatch = new CountDownLatch(1);
    private static final CountDownLatch finishLatch = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        Set<AMapWebConfig> set = new HashSet<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    startLatch.await(); // 等待所有线程都准备好
                    AMapWebConfig config = AMapWebConfig.getKey();
                    set.add(config);
                    if (set.size() > 1) {
                        System.out.println("stop" + set.size());
                    }
                    finishLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        
        // 让所有线程同时开始
        startLatch.countDown();
        
        // 等待所有线程执行完毕
        finishLatch.await();
    }
}
