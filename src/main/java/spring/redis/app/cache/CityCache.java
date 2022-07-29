package spring.redis.app.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spring.redis.app.dto.model.City;

import java.util.List;

@Service(value = "cityCache")
@Slf4j
public class CityCache implements AbstractCache<City, Long> {

    private final HashOperations<String, Long, City> hashOperations;
    private final String keyCityHash;
    private final RedisTemplate<String, City> redisTemplate;

    @Autowired
    public CityCache(RedisTemplate<String, City> redisTemplate, @Value("${cache.city.hash}") String keyCityHash) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.keyCityHash = keyCityHash;
    }

    @Override
    public City getById(Long id) {
        if (exists(id, keyCityHash)) {
            return hashOperations.get(keyCityHash, id);
        } else {
            log.warn("Запись с ID - {} отсутствует. Получение неуспешно.", id);
            return City.invalidateCity();
        }
    }

    @Override
    public void add(City value) {
        hashOperations.put(keyCityHash, value.getId(), value);
        log.info("Запись успешно добавлена. Город - {}.", value.getCity());
    }

    @Override
    public boolean delete(Long id) {
        if (exists(id, keyCityHash)) {
            hashOperations.delete(keyCityHash, id);
            return true;
        } else {
            log.warn("Запись с ID - {} отсутствует. Удаление неуспешно.", id);
            return false;
        }
    }

    @Override
    public boolean update(Long id, City value) {
        if (exists(id, keyCityHash)) {
            hashOperations.put(keyCityHash, id, value);
            return true;
        } else {
            log.warn("Запись с ID - {} отсутствует. Обновление неуспешно.", id);
            return false;
        }
    }

    @Override
    public List<City> findAll() {
        return hashOperations.values(keyCityHash);
    }

    @Override
    @Scheduled(fixedDelay = 30_000)
    public void clear() {
        boolean success = Boolean.TRUE.equals(redisTemplate.delete(keyCityHash));
        if (success) {
            log.info("Успешная очистка кеша. Размер кеша - {}", findAll().size());
        } else {
            log.info("Ключ с значениями не существует");
        }
    }

    private boolean exists(Long id, String keyCityHash) {
        return hashOperations.hasKey(keyCityHash, id);
    }
}
