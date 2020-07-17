package com.mediasoft.tm.config.security.aurhorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Setter
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private String TOKEN_HEADER;

    private String TOKEN_PREFIX;

    private String TOKEN_SECRET;

    private final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, Environment env) {
        super(authenticationManager);
        String hw = env.getProperty("pikoff.conf.hw");
        this.TOKEN_SECRET = env.getProperty("security.token.secret_key");
        this.TOKEN_HEADER = env.getProperty("security.token.header");
        this.TOKEN_PREFIX = env.getProperty("security.token.prefix");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(TOKEN_HEADER);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        this.logData(
                this.getTokenFrom(req)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private void logData(DecodedJWT token) {
        logger.debug("Token header: " + token.getHeader());
        logger.debug("Token payload: " + token.getPayload());
        logger.debug("Token subject: " + token.getSubject());
        logger.debug("Token expiration date: " + token.getExpiresAt());
    }

    private UsernamePasswordAuthenticationToken getAuthentication(
            HttpServletRequest request) throws JsonProcessingException {
        var token = this.getTokenFrom(request);
        if (Objects.isNull(token)) {
            return null;
        }
        var email = token.getSubject();
        var authorities = new ObjectMapper().readValue(
                token.getClaim("authorities").asString(),
                ContributionGrantedAuthority[].class
        );
        if (Objects.nonNull(email)) {
            return new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    List.of(authorities));
        }
        return null;
    }

    private DecodedJWT getTokenFrom(HttpServletRequest request) {
        String tokenHeader = request.getHeader(TOKEN_HEADER);
        return Objects.isNull(tokenHeader) ? null : this.decodeJWT(tokenHeader);
    }

    private DecodedJWT decodeJWT(String token) {
        return JWT
                .require(this.byAlgorithm())
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""));
    }

    private Algorithm byAlgorithm() {
        return Algorithm.HMAC512(TOKEN_SECRET.getBytes());
    }
}