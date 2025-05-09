package eg.intercom.ppo.revamp.productqueryservice.dtos;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductViewDto(
        String id,
        String name,
        String description,
        double price,
        int quantity,
        LocalDateTime creationDate,
        String createdBy,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy,
        String eventType
) {
}
