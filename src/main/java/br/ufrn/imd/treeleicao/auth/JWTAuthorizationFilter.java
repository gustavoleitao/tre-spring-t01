package br.ufrn.imd.treeleicao.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static br.ufrn.imd.treeleicao.auth.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsServiceImpl userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        String fullToken = req.getHeader(HEADER_STRING);
        String token = fullToken.replace(TOKEN_PREFIX, "");
        var auth = tryGetAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken tryGetAuthentication(String token){
        try{
            return getAuthentication(token);
        }catch (Exception ex){
            return null;
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (Objects.isNull(token)) return null;
        var jwtVerify = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build();
        var jwt = jwtVerify.verify(token.replace(TOKEN_PREFIX, ""));
        String user = jwt.getSubject();
        var claim = jwt.getClaim("authorities");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (Objects.nonNull(claim)) {
            var roles = claim.asList(String.class);
            roles.stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
        if (Objects.isNull(user)) return null;
        return UsernamePasswordAuthenticationToken.authenticated(userDetailsService.loadUserByUsername(user), null, authorities);
    }

}
