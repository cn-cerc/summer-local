package cn.cerc.local.amap;

import java.util.HashSet;
import java.util.Set;

public class AMapWebConfigTest1 {
    private static final int THREAD_COUNT = 500000;
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
