package com.smartcard.Config;

import com.smartcard.Repository.AppUserRepository;
import com.smartcard.Service.JwtService;
import com.smartcard.entity.AppUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;
import java.util.Collections;
import java.util.Optional;
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private AppUserRepository appUserRepository;

    public JWTRequestFilter(JwtService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        System.out.println(tokenHeader);

        if (tokenHeader !=null && tokenHeader.startsWith("Bearer")){
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);
            System.out.println(token);

            String username = jwtService.getUsername(token);
            System.out.println(username);

            Optional<AppUser> byUsername = appUserRepository.findByUsername(username);
            if (byUsername.isPresent()){
                AppUser appUser = byUsername.get();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(appUser,null,Collections.singleton(new SimpleGrantedAuthority(appUser.getRole())));
             authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request,response);
    }

}
