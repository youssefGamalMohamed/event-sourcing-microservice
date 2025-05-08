package eg.intercom.ppo.revamp.productcommandservice.controllers;

import eg.intercom.ppo.revamp.productcommandservice.dtos.ProductDto;
import eg.intercom.ppo.revamp.productcommandservice.mappers.ProductMapper;
import eg.intercom.ppo.revamp.productcommandservice.models.Product;
import eg.intercom.ppo.revamp.productcommandservice.services.ProductServiceIfc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class ProductController {

    private final ProductServiceIfc productService;
    private final ProductMapper productMapper;

    public ProductController(ProductServiceIfc productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductDto product) {
        log.info("Creating product: {}", product);

        Product productEntity = productMapper.toEntity(product);
        log.info("Mapped product DTO to entity: {}", productEntity);
        Product createdProduct = productService.createProduct(productEntity);
        log.info("Created product: {}", createdProduct);

        return createdProduct;
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable String id, @RequestBody ProductDto product) {
        log.info("Updating product with id: {} with new data: {}", id, product);

        Product productEntity = productMapper.toEntity(product);
        log.info("Mapped product DTO to entity: {}", productEntity);
        productService.updateProduct(id, productEntity);
        log.info("Updated product with id: {}", id);

    }
}
