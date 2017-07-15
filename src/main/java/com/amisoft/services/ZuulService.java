package com.amisoft.services;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by amitdatta on 15/07/17.
 */

@Service
public class ZuulService {

    private static final String DOUBLE_STAR = "**";

    @Autowired
    private ZuulProperties zuulProperties;

    @Value("${server.port}")
    private Integer port;

    public List<com.amisoft.model.Route> getRegisteredServices() throws UnknownHostException{

        Map<String,ZuulProperties.ZuulRoute> zuulPropertiesRoute = zuulProperties.getRoutes();
        List<com.amisoft.model.Route> routes = new ArrayList<>();

        for(Map.Entry<String,ZuulProperties.ZuulRoute>  entry: zuulPropertiesRoute.entrySet()){

            ZuulProperties.ZuulRoute zuulRoute = entry.getValue();
            com.amisoft.model.Route route = generateRoute(zuulRoute);
            routes.add(route);
        }

        return routes;
    }

    private com.amisoft.model.Route generateRoute(ZuulProperties.ZuulRoute zuulRoute) throws UnknownHostException{

        com.amisoft.model.Route route = new com.amisoft.model.Route();
        route.setName(zuulRoute.getId());
        route.setPath(generateUrl(zuulRoute));
        route.setServiceId(zuulRoute.getServiceId());
        return route;

    }

    private String generateUrl(ZuulProperties.ZuulRoute zuulRoute) {

        return StringUtils.replace(zuulRoute.getPath(),DOUBLE_STAR,StringUtils.EMPTY);
    }

}
