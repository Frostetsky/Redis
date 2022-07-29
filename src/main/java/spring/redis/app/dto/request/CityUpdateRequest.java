package spring.redis.app.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityUpdateRequest {

    private String city;

    private String country;

    private String createTime;
}
