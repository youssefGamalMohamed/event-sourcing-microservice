package eg.intercom.ppo.revamp.productqueryservice.services;

import eg.intercom.ppo.revamp.productqueryservice.models.ProductView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductViewService {

    ProductView addProductView(ProductView productView);

    Page<ProductView> findAllByOriginalId(String id, Pageable pageable);

    ProductView findByOriginalIdAndWithLastHistory(String originalId);

}
