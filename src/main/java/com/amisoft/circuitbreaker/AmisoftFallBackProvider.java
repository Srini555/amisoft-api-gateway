package com.amisoft.circuitbreaker;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by amitdatta on 15/07/17.
 */
public class AmisoftFallBackProvider implements ZuulFallbackProvider{

    public static final String ONE_STAR = "*";
    private String responseBody = "{\"message\":\"Service unavailable. Please try after sometime";
    private HttpHeaders headers = null;
    private String route = null;
    private int rawStatusCode = 503;
    private HttpStatus statusCode = HttpStatus.SERVICE_UNAVAILABLE;
    private String statusText = "Service Unavailable";

    public void setRoute(String route){
        this.route = route;
    }

    @Override
    public String getRoute() {
        return route==null?ONE_STAR:route;
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {

                if(null == statusCode){
                    statusCode = HttpStatus.SERVICE_UNAVAILABLE;
                }
                return statusCode;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return rawStatusCode;
            }

            @Override
            public String getStatusText() throws IOException {
                return statusText;
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(responseBody.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                if (null == headers) {
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                }
                return headers;
            }
        };
    }
}
