package eg.intercom.ppo.revamp.productqueryservice.mappers;

import eg.intercom.ppo.revamp.productcommandservice.events.ProductEvent;
import eg.intercom.ppo.revamp.productqueryservice.dtos.ProductViewDto;
import eg.intercom.ppo.revamp.productqueryservice.models.ProductView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductViewMapper {


    @Mapping(ignore = true, target = "id")
    @Mapping(source = "eventType", target = "eventType")
    @Mapping(source = "product.id", target = "originalId")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.description", target = "description")
    @Mapping(source = "product.price", target = "price")
    @Mapping(source = "product.quantity", target = "quantity")
    @Mapping(source = "product.creationDate", target = "creationDate")
    @Mapping(source = "product.createdBy", target = "createdBy")
    @Mapping(source = "product.lastModifiedDate", target = "lastModifiedDate")
    @Mapping(source = "product.lastModifiedBy", target = "lastModifiedBy")
    ProductView toProductView(ProductEvent productEvent);

    @Mapping(source = "productView.originalId", target = "id")
    ProductViewDto toDto(ProductView productView);

}
