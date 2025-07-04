package eg.intercom.ppo.revamp.productcommandservice.auditing;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@AllArgsConstructor
public class SecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.of("UNKNOWN");
//        }
//        return Optional.of(authentication.getName());

        return Optional.of("System-Admin");
    }

}