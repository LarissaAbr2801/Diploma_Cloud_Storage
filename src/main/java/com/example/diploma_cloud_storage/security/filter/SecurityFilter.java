package com.example.diploma_cloud_storage.security.filter;

import com.example.diploma_cloud_storage.constant.SecurityConstants;
import com.example.diploma_cloud_storage.entity.User;
import com.example.diploma_cloud_storage.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public SecurityFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = httpServletRequest.getHeader(SecurityConstants.AUTH_TOKEN);

        if (bearerToken == null || !bearerToken.startsWith(SecurityConstants.JWT_TOKEN_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String authToken = bearerToken.replace(SecurityConstants.JWT_TOKEN_PREFIX, "");
        authToken = authToken.replace(" ", "");

        Optional<User> userOptional = userRepository.findByAuthToken(authToken);

        if (userOptional.isEmpty()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        User user = userOptional.get();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}

