package com.leo.ourproperty.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService detailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("dofilter");
        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);
        if(token == null || !token.startsWith(JwtUtils.JWT_BEARER)){
            filterChain.doFilter(request, response);
            return;
        }

        if(!JwtUtils.isTokenValid(token)){
            filterChain.doFilter(request, response);
            return;
        }

        String username = JwtUtils.getUsernameFromToken(token);
        log.info(username);
        toAuthentication(request, username);
        filterChain.doFilter(request, response);

    }
    private void toAuthentication(HttpServletRequest request, String email){
        log.info("toauth");
        UserDetails userDetails = detailsService.loadUserByUsername(email);
        log.info(userDetails.toString());
        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
