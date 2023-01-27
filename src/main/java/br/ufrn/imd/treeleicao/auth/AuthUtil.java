package br.ufrn.imd.treeleicao.auth;

import br.ufrn.imd.treeleicao.user.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class AuthUtil {

    public static Collection<GrantedAuthority> toSimpleAuthority(Collection<Role> roles){
        if (roles.isEmpty()) return Collections.EMPTY_LIST;
        return roles.stream()
                .filter(role -> Objects.nonNull(role))
                .map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    public static Collection<GrantedAuthority> toSimpleAuthority(Role role){
        return toSimpleAuthority(Arrays.asList(role));
    }

    public static List<String> toRoles(UserSpringDetails springDetailsD){
        return toRoles(springDetailsD.getAuthorities());
    }

    public static List<String> toRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());
    }
}
