package com.company_manager.api.security;

import com.company_manager.api.exceptions.GlobalExceptionHandler;
import com.company_manager.api.models.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
        private final AuthenticationManager authenticationManager;
        private final JWTUtil jwtUtil;

        public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
            setAuthenticationFailureHandler(new GlobalExceptionHandler());
            this.authenticationManager = authenticationManager;
            this.jwtUtil = jwtUtil;
        }


        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            try {
                UserModel userCredentials = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userCredentials.getUsername(), userCredentials.getPassword(), new ArrayList<>()
                );
                return this.authenticationManager.authenticate(authToken);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException, ServletException {
            UserSpringSecurity userSpringSecurity = (UserSpringSecurity) authentication.getPrincipal();
            String username = userSpringSecurity.getUsername();
            String token = this.jwtUtil.generateToken(username);
            response.addHeader("Authorization", "Bearer " + token);
            response.addHeader("access-control-expose-headers", "Authorization");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);

            String json = new ObjectMapper().writeValueAsString(tokenMap);
            response.getWriter().write(json);

        }
}
