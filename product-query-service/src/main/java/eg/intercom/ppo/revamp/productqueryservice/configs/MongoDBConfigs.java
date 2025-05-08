package eg.intercom.ppo.revamp.productqueryservice.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "eg.intercom.ppo.revamp.productqueryservice.repos")
public class MongoDBConfigs {
}
