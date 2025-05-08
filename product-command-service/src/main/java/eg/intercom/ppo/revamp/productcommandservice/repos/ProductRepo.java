package eg.intercom.ppo.revamp.productcommandservice.repos;

import eg.intercom.ppo.revamp.productcommandservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {
}
