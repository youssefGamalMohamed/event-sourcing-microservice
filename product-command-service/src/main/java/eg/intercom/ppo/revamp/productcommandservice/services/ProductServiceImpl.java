package eg.intercom.ppo.revamp.productcommandservice.services;

import eg.intercom.ppo.revamp.productcommandservice.models.Product;
import eg.intercom.ppo.revamp.productcommandservice.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductServiceIfc {

    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product);

        Product newProduct = productRepo.save(product);

        log.info("Product created with id = {}", newProduct.getId());

        return newProduct;
    }
}
