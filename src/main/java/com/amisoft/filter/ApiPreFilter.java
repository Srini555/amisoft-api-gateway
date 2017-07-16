package com.amisoft.filter;

import com.amisoft.dao.EmployeeDao;
import com.amisoft.entity.Employee;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by amitdatta on 16/07/17.
 */
public class ApiPreFilter extends ZuulFilter {


    private static final String EMPLOYEE_NAME = "name";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String PRE = "pre";
    public static final String ACCESS_DENIED = "Access denied";


    @Value(value = "${oauth.server.url.token}")
    private String oauthTokenUri;

    @Value(value = "${oauth.server.clientId}")
    private String clientId;

    @Value(value = "${oauth.server.clientSecret}")
    private String clientPass;


    @Autowired
    EmployeeDao employeeDao;



    @Override
    public String filterType() {
        return PRE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String employeeName = request.getParameter(EMPLOYEE_NAME);
        Employee emp = employeeDao.findEmployeeByName(employeeName);

        if(null == emp){
            ctx.setResponseBody(ACCESS_DENIED);
            throw new AccessDeniedException(ACCESS_DENIED);
        }else{

            String accessCode = emp.getPassword();
            OAuth2RestTemplate template = getOauthToken(employeeName,accessCode);
            ctx.addZuulRequestHeader(AUTHORIZATION, BEARER+template.getAccessToken().toString());
        }

       return null;

    }

    private OAuth2RestTemplate getOauthToken(String employeeName, String accessCode) {

        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
        resourceDetails.setAccessTokenUri(oauthTokenUri);
        resourceDetails.setScope(Arrays.asList("read"));
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientPass);
        resourceDetails.setUsername(employeeName);
        resourceDetails.setPassword(accessCode);

        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails);
        System.out.println("********** :"+template.getAccessToken().toString());
        return template;
    }
}
