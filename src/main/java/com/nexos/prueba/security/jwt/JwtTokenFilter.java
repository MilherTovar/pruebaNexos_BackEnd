package com.nexos.prueba.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nexos.prueba.security.service.UserDetailsServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {

    public final static Logger logger=LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {  
                try{
                    String token=getToken(req);
                    if(token!=null && jwtProvider.validateToken(token)){
                        String emailUsuario=jwtProvider.getEmailFromToken(token);
                        UserDetails userdetails=userDetailsService.loadUserByUsername(emailUsuario);
                        UsernamePasswordAuthenticationToken auth=
                            new UsernamePasswordAuthenticationToken(userdetails, null,userdetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }catch(Exception e){
                    logger.error("Falla en el método doFilter");
                }
                filterChain.doFilter(req, res);

    }

    private String getToken(HttpServletRequest request){
        String header=request.getHeader("Authorization");
        if(header!=null && header.startsWith(("Bearer")))
            return header.replace("Bearer", "");
        return null;
    }
    
}
