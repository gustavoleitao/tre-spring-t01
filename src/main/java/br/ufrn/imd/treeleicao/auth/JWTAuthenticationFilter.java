package br.ufrn.imd.treeleicao.auth;

import br.ufrn.imd.treeleicao.user.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import static br.ufrn.imd.treeleicao.auth.SecurityConstants.EXPIRATION_TIME;
import static br.ufrn.imd.treeleicao.auth.SecurityConstants.SECRET;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {

    private record Token(String accessToken){}

    public JWTAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserDTO user = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
            return getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(UserSpringDetails.from(user),
                            user.getPassword(), AuthUtil.toSimpleAuthority(user.getRole())));
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserSpringDetails userDetails = (UserSpringDetails) authResult.getPrincipal();
        if (authResult.isAuthenticated()){
            var tokenResult = generateToken(userDetails, authResult.getAuthorities());
            writeResult(response, new ObjectMapper().writeValueAsString(tokenResult), HttpStatus.OK);
        }else{
            writeResult(response, "Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    private void writeResult(HttpServletResponse response, String result, HttpStatus status) throws IOException {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(status.value());
        response.getWriter().write(result);
        response.getWriter().flush();
    }

    private Token generateToken(UserSpringDetails springDetails, Collection<? extends GrantedAuthority> authorities) {
        String token = JWT.create()
                .withSubject(springDetails.getUsername())
                .withClaim("authorities", AuthUtil.toRoles(authorities))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        var tokenObj = new Token(token);
        return tokenObj;
    }
}
