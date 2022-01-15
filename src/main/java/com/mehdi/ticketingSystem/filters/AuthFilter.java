package com.mehdi.ticketingSystem.filters;

import com.mehdi.ticketingSystem.ConstantManager;
import com.mehdi.ticketingSystem.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        ConstantManager constantManager = ConstantManager.getInstance();

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String token = httpRequest.getHeader(constantManager.AUTH_KEY);
        if (token == null) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
            return;
        }
        try {
            Claims claims = Jwts.parser().setSigningKey(constantManager.API_SECRET_KEY).parseClaimsJws(token).getBody();
            httpRequest.setAttribute(User.USER_ID, Integer.parseInt(claims.get(User.USER_ID).toString()));
        } catch (Exception e) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
