package eg.intercom.ppo.revamp.productqueryservice.controllers;

import eg.intercom.ppo.revamp.productqueryservice.dtos.ProductViewDto;
import eg.intercom.ppo.revamp.productqueryservice.mappers.ProductViewMapper;
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
    private final ProductViewMapper productViewMapper;

    public ProductViewController(ProductViewService productViewService, ProductViewMapper productViewMapper) {
        this.productViewService = productViewService;
        this.productViewMapper = productViewMapper;
    }


    @GetMapping("/products/{id}/history")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductViewDto> findAllHistoryByOriginalId(@PathVariable(name = "id") String id, @ParameterObject Pageable pageable) {
        log.info("findById called with id: {},pageable:{}", id, pageable);
        Page<ProductView> productViewPage = productViewService.findAllByOriginalId(id, pageable);
        log.info("ProductView found with id: {}, TotalElement:{}", id, productViewPage.getTotalElements());
        return productViewPage.map(productViewMapper::toDto);
    }

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductViewDto findByOriginalIdAndWithLastHistory(@PathVariable(name = "id") String id) {
        log.info("findById called with id: {}", id);
        ProductView productView = productViewService.findByOriginalIdAndWithLastHistory(id);
        log.info("ProductView found with id: {}", id);
        return productViewMapper.toDto(productView);
    }
}
