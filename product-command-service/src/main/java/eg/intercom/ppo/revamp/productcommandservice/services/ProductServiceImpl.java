package eg.intercom.ppo.revamp.productcommandservice.services;

import eg.intercom.ppo.revamp.productcommandservice.enums.ProductEventType;
import eg.intercom.ppo.revamp.productcommandservice.mappers.ProductMapper;
import eg.intercom.ppo.revamp.productcommandservice.models.Product;
import eg.intercom.ppo.revamp.productcommandservice.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class ProductServiceImpl implements ProductServiceIfc {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final ProductEventProducerIfc productEventProducer;

    public ProductServiceImpl(ProductRepo productRepo, ProductMapper productMapper, ProductEventProducerIfc productEventProducer) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
        this.productEventProducer = productEventProducer;
    }

    @Override
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product);

        Product newProduct = productRepo.save(product);

        log.info("Product created with id = {}", newProduct.getId());

        log.info("Will Publish Product Created Event");

        // publish product created event
        productEventProducer.publish(productMapper.toEvent(newProduct, ProductEventType.CREATED));

        return newProduct;
    }

    @Override
    public Product updateProduct(String id, Product product) {
        log.info("Updating product with id: {} with new data: {}", id, product);
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
        productMapper.updateFrom(product, existingProduct);
        Product updatedProduct = productRepo.save(existingProduct);
        log.info("Product updated with id = {}", updatedProduct.getId());

        // publish product created event
        productEventProducer.publish(productMapper.toEvent(updatedProduct, ProductEventType.UPDATED));

        return updatedProduct;
    }


}
