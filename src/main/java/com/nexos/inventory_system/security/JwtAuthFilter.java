package com.nexos.inventory_system.security;

import com.nexos.inventory_system.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    public JwtAuthFilter(JwtUtil jwtUtil, UserRepository userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);
        username = jwtUtil.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user = userRepo.findByUsername(username).orElse(null);
            if (user != null && jwtUtil.validateToken(token)) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getRoles()
                );


                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

                request.setAttribute("userId", user.getId());
            }
        }

        filterChain.doFilter(request, response);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.warn("Auth: ❌ not Authentication");
        }
    }
}
