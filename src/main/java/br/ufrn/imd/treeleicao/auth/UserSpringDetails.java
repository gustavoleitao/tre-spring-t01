package br.ufrn.imd.treeleicao.auth;

import br.ufrn.imd.treeleicao.user.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserSpringDetails implements UserDetails {

    private final UserDTO user;

    private UserSpringDetails(UserDTO user) {
        this.user = user;
    }

    public static UserSpringDetails from(UserDTO user){
        return new UserSpringDetails(user);
    }

    public static UserSpringDetails from(String user, String pass){
        var userDto = new UserDTO();
        userDto.setUsername(user);
        userDto.setPassword(pass);
        return new UserSpringDetails(userDto);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.isNull(user.getRole())) return Collections.emptyList();
        var authority = new SimpleGrantedAuthority(user.getRole().name());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
