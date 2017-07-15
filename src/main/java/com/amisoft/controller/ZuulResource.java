package com.amisoft.controller;

import com.amisoft.model.Route;
import com.amisoft.services.ZuulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by amitdatta on 15/07/17.
 */

@RestController
@RequestMapping(value = "/api")
public class ZuulResource {

    @Autowired
    private ZuulService zuulService;


    @RequestMapping(value="registeredServices", method = RequestMethod.GET , produces = APPLICATION_JSON_VALUE)
    public List<Route>  getRegisteredServices() throws UnknownHostException{
        return zuulService.getRegisteredServices();
    }

}
