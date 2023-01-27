package br.ufrn.imd.treeleicao.auth;

import br.ufrn.imd.treeleicao.user.UserDTO;
import br.ufrn.imd.treeleicao.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService service;

    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = service.findUserByUsername(username);
        return UserSpringDetails.from(user);
    }

}
