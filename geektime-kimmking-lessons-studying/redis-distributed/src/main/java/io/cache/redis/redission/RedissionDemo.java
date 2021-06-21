package io.cache.redis.redission;

import lombok.SneakyThrows;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissionDemo {

    // 可参阅：https://www.jianshu.com/p/47fd7f86c848

    @SneakyThrows
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6380");

        final RedissonClient client = Redisson.create(config);
        RMap<String, String> rMap = client.getMap("map1");
        RLock lock = client.getLock("locak1");

        try {
            lock.lock();

            for (int i = 0; i < 15; i++) {
                rMap.put("rKey-"+i,"rValue-"+i);
            }

        }finally {
            lock.unlock();
        }

        while (true){
            Thread.sleep(1000);
            System.out.println(rMap.get("rKey-10"));
        }

    }
}
