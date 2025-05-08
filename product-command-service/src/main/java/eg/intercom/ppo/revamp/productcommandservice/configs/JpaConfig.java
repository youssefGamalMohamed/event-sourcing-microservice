package eg.intercom.ppo.revamp.productcommandservice.configs;

import eg.intercom.ppo.revamp.productcommandservice.auditing.SecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {


    @Bean
    AuditorAware<String> auditorProvider() {
        return new SecurityAuditorAware();
    }

}
