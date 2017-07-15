package com.amisoft.circuitbreaker;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by amitdatta on 15/07/17.
 */

@Configuration
public class AmisoftZuulFallBackProviderConfig {

    private static final String AMISOFT_SERVICE_WELCOME = "amisoft-service-welcome";


    @Bean
    public ZuulFallbackProvider routeAmisoftServiceWelcome(){

        AmisoftFallBackProvider routeAmisoftServiceWelcomeFallback = new AmisoftFallBackProvider();
        routeAmisoftServiceWelcomeFallback.setRoute(AMISOFT_SERVICE_WELCOME);
        return routeAmisoftServiceWelcomeFallback;
    }


}
