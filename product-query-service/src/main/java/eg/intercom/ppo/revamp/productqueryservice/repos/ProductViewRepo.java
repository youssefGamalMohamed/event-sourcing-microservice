package eg.intercom.ppo.revamp.productqueryservice.repos;

import eg.intercom.ppo.revamp.productqueryservice.models.ProductView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductViewRepo extends MongoRepository<ProductView, String> {

    Page<ProductView> findAllByOriginalId(String originalId, Pageable pageable);

    Optional<ProductView> findFirstByOriginalIdOrderByLastModifiedDateDesc(String originalId);
}
