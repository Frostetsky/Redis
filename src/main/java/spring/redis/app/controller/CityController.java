package spring.redis.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.redis.app.cache.AbstractCache;
import spring.redis.app.dto.model.City;
import spring.redis.app.dto.ResponseType;
import spring.redis.app.dto.request.CityUpdateRequest;
import spring.redis.app.mapper.CityMapper;
import spring.redis.app.util.Constants;

@RestController
@RequestMapping("/city")
public class CityController {

    private final AbstractCache<City, Long> redisCache;
    private final CityMapper cityMapper;

    @Autowired
    public CityController(@Qualifier("cityCache") AbstractCache<City, Long> redisCache, CityMapper cityMapper) {
        this.redisCache = redisCache;
        this.cityMapper = cityMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<String> addCity(@RequestBody City city) {
        redisCache.add(city);
        return ResponseEntity
                .ok()
                .body(ResponseType.SUCCESS.name());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCity(@RequestParam("id") Long id, @RequestBody CityUpdateRequest cityUpdate) {
        var city = cityMapper.cityToCityRequest(cityUpdate);
        var rsl =  redisCache.update(id, city);
        return rsl
                ? ResponseEntity.ok().body(String.format("Запись с ID - %s обновлена.", id))
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/getBy")
    public ResponseEntity<City> getById(@RequestParam("id") Long id) {
        var rsl = redisCache.getById(id);
        return rsl.getId() != Constants.INVALID_ID
                ? ResponseEntity.ok().body(rsl)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteBy")
    public ResponseEntity<String> deleteById(@RequestParam("id") Long id) {
        var rsl = redisCache.delete(id);
        return rsl
                ? ResponseEntity.ok().body(String.format("Запись с ID - %s удалена.", id))
                : ResponseEntity.notFound().build();
    }
}
