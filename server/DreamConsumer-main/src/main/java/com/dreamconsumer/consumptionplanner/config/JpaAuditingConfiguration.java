package com.dreamconsumer.consumptionplanner.config;

import com.dreamconsumer.consumptionplanner.base.SpringSecurityAuditorAware;
import com.dreamconsumer.consumptionplanner.member.domain.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    @Bean
    public SpringSecurityAuditorAware auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}
