package eg.intercom.ppo.revamp.productcommandservice.services;

import eg.intercom.ppo.revamp.productcommandservice.models.Product;

public interface ProductServiceIfc {

    Product createProduct(Product product);

    Product updateProduct(String id, Product product);
}
