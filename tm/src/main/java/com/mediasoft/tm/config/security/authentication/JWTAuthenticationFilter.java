package com.mediasoft.tm.config.security.authentication;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Setter
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final String TOKEN_HEADER;

    private final String TOKEN_PREFIX;

    private final Long TOKEN_EXPIRATION_TIME;

    private final String TOKEN_SECRET;

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, Environment env) {
        this.authenticationManager = authenticationManager;
        this.TOKEN_SECRET = env.getProperty("security.token.secret_key");
        this.TOKEN_HEADER = env.getProperty("security.token.header");
        this.TOKEN_PREFIX = env.getProperty("security.token.prefix");
        this.TOKEN_EXPIRATION_TIME = Long.parseLong(env.getProperty("security.token.expiration_time"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            Credentials creds = this.parseCredentialsFrom(req);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
            var user = (User) auth.getPrincipal();
            String token = this.generateTokenFrom(user);
            res.addHeader(TOKEN_HEADER, TOKEN_PREFIX + token);
    }

    private Credentials parseCredentialsFrom(HttpServletRequest req) throws IOException {
        return new ObjectMapper()
                .readValue(req.getInputStream(), Credentials.class);
    }

    private String generateTokenFrom(User user) throws JsonProcessingException {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("authorities",
                        new ObjectMapper().writeValueAsString(user.getAuthorities()))
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(TOKEN_SECRET.getBytes()));
    }
}
