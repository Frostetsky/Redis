package spring.redis.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import spring.redis.app.dto.model.City;

@Configuration
public class RedisConfiguration {

    @Value("${cache.city.hash}")
    private String cacheKey;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        final var redis = new RedisStandaloneConfiguration();
        redis.setHostName("localhost");
        redis.setPort(6379);
        return new JedisConnectionFactory(redis);
    }

    @Bean
    public RedisTemplate<String, City> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        final var template = new RedisTemplate<String, City>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
