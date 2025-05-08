package eg.intercom.ppo.revamp.productqueryservice.repos;

import eg.intercom.ppo.revamp.productqueryservice.models.ProductView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductViewRepo extends MongoRepository<ProductView, UUID> {
}
