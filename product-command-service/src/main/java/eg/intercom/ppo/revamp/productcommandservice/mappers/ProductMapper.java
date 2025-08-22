package eg.intercom.ppo.revamp.productcommandservice.mappers;

import eg.intercom.ppo.revamp.productcommandservice.dtos.ProductDto;
import eg.intercom.ppo.revamp.productcommandservice.events.ProductEvent;
import eg.intercom.ppo.revamp.productcommandservice.models.Product;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product product);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDto productDto);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateFrom(Product source, @MappingTarget Product target);

    
    // ✅ Explicitly map LocalDateTime → Instant using conversion functions
    @Mapping(target = "creationDate", expression = "java(map(product.getCreationDate()))")
    @Mapping(target = "lastModifiedDate", expression = "java(map(product.getLastModifiedDate()))")
    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "timestamp", expression = "java(java.time.Instant.now().toEpochMilli())")
    ProductEvent toEvent(Product product, String eventType);

    // === Conversion methods ===
    default Instant map(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC) : null;
    }

    default LocalDateTime map(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }
}
