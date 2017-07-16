package com.amisoft.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by amitdatta on 16/07/17.
 */

@Configuration
public class ApiFilterConfig {

    @Bean
    public ApiPreFilter apiOauthPreFilter() {
        return new ApiPreFilter();
    }
}
