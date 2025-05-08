package eg.intercom.ppo.revamp.productcommandservice.mappers;

import eg.intercom.ppo.revamp.productcommandservice.dtos.ProductDto;
import eg.intercom.ppo.revamp.productcommandservice.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);

    void updateFrom(Product source, @MappingTarget Product target);
}
