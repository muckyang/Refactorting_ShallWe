package com.web.blog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

    private final EntityManager em;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public SpringConfig(EntityManager em) {
        logger.debug("make EntityManager ");
        this.em = em;
    }

}