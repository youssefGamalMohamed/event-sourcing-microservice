package eg.intercom.ppo.revamp.productqueryservice;

import org.springframework.boot.SpringApplication;

public class TestProductQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductQueryServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
