package eg.intercom.ppo.revamp.productcommandservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "Product Data Transfer Object", name = "Product")
public record ProductDto(
        String id,
        String name,
        String description,
        double price,
        int quantity,
        LocalDateTime creationDate,
        String createdBy,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy
) {
}
