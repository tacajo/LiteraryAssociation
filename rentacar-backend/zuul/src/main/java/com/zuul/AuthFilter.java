package com.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class AuthFilter extends ZuulFilter {

    private String authUrl = "http://localhost:8081/auth/validate-token";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthClient authClient;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//
//        if ( ctx.getRequest().getRequestURI() == null ||
//                ! ctx.getRequest().getRequestURI().startsWith("/auth"))
//            return false;
//        return ctx.getRouteHost() != null && ctx.sendZuulResponse();
        return true;
    }

    private void setFailedRequest(String body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("Postoji Bearer token");
            // create request and send it
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authHeader.substring(7));
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<TokenValidationResponse> responseEntity = restTemplate.exchange(authUrl, HttpMethod.GET,
                    entity, TokenValidationResponse.class);
            TokenValidationResponse response = responseEntity.getBody();
            // if token is valid add data
            if (response.getIsValid()) {
                System.out.println("Bearer token je validan");
                System.out.println(response.getAuthorities());
                ctx.addZuulRequestHeader("UserId", response.getUserId().toString());
                ctx.addZuulRequestHeader("Username", response.getUsername());
                ctx.addZuulRequestHeader("Authorities", response.getAuthorities());
            }
        }
        return null;
    }
}
