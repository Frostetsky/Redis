package spring.redis.app.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class City implements Serializable {

    private long id;

    private String country;

    private String city;

    private String createTime;

    public static City invalidateCity() {
        var inv = new City();
        inv.setId(-1L);
        return inv;
    }
}
