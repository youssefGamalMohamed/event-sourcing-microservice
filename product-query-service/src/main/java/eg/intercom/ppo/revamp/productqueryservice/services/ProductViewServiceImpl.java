package eg.intercom.ppo.revamp.productqueryservice.services;

import eg.intercom.ppo.revamp.productqueryservice.models.ProductView;
import eg.intercom.ppo.revamp.productqueryservice.repos.ProductViewRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductViewServiceImpl implements ProductViewService {

    private final ProductViewRepo productViewRepo;

    public ProductViewServiceImpl(ProductViewRepo productViewRepo) {
        this.productViewRepo = productViewRepo;
    }

    @Override
    public ProductView addProductView(ProductView productView) {
        log.info("addProductView called with productView: {}", productView);
        ProductView savedProductView = productViewRepo.save(productView);
        log.info("ProductView saved with id: {}", savedProductView.getId());
        return savedProductView;
    }
}
