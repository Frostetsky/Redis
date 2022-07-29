package spring.redis.app;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SpringRedisApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(UUID.randomUUID().toString());
    }

}
