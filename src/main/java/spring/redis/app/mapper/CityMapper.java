package spring.redis.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import spring.redis.app.dto.model.City;
import spring.redis.app.dto.request.CityUpdateRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
@Component
public interface CityMapper {

    @Mapping(target = "city", source = "cityUpdateRequest.city")
    @Mapping(target = "country", source = "cityUpdateRequest.country")
    @Mapping(target = "createTime", source = "cityUpdateRequest.createTime")
    @Mapping(target = "id", ignore = true)
    public City cityToCityRequest(CityUpdateRequest cityUpdateRequest);
}
