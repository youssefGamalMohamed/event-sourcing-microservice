package eg.intercom.ppo.revamp.productqueryservice.controllers;

import eg.intercom.ppo.revamp.productqueryservice.models.ProductView;
import eg.intercom.ppo.revamp.productqueryservice.services.ProductViewService;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProductViewController {


    private final ProductViewService productViewService;

    public ProductViewController(ProductViewService productViewService) {
        this.productViewService = productViewService;
    }


    @GetMapping("/products/{originalId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductView> findByAllByOriginalId(@PathVariable(name = "originalId") String id, @ParameterObject Pageable pageable) {
        log.info("findById called with id: {},pageable:{}", id, pageable);
        Page<ProductView> productViewPage = productViewService.findAllById(id, pageable);
        log.info("ProductView found with id: {}, TotalElement:{}", id, productViewPage.getTotalElements());
        return productViewPage;
    }
}
