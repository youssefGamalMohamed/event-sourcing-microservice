package eg.intercom.ppo.revamp.productcommandservice.dtos;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
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
