package eg.intercom.ppo.revamp.productcommandservice.mappers;

import eg.intercom.ppo.revamp.productcommandservice.dtos.ProductDto;
import eg.intercom.ppo.revamp.productcommandservice.enums.ProductEventType;
import eg.intercom.ppo.revamp.productcommandservice.events.ProductEvent;
import eg.intercom.ppo.revamp.productcommandservice.models.Product;
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


    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "product", source = "product")
    ProductEvent toEvent(Product product, ProductEventType eventType);

}
