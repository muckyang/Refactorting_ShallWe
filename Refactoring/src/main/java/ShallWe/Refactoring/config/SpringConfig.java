package ShallWe.Refactoring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;


@Configuration
@EnableJpaAuditing
public class SpringConfig {
    EntityManager em ;
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

}
