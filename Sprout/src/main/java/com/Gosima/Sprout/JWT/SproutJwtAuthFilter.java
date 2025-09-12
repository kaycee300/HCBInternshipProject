package com.Gosima.Sprout.JWT;

import com.Gosima.Sprout.User.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class SproutJwtAuthFilter extends OncePerRequestFilter {

    private final SproutJwtService jwtService;

    private final MyUserDetailsService userDetailsService;

    public SproutJwtAuthFilter(SproutJwtService jwtService, MyUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader= request.getHeader("Authorization");
        final String prefix = "Bearer ";


        String jwt = null;
        String username = null;

        if (authHeader !=null && authHeader.startsWith(prefix)){
            jwt = authHeader.substring(7);

            try {
                username= jwtService.extractUsername(jwt);
            } catch (Exception e) {
                System.out.println("Invalid Token");
                throw new RuntimeException(e);
            }

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() ==null){
            UserDetails user= userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, user)){
                UsernamePasswordAuthenticationToken AuthToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                AuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(AuthToken);


            }
        }
        filterChain.doFilter(request, response);

    }
}
