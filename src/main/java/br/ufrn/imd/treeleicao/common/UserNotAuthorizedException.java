package br.ufrn.imd.treeleicao.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserNotAuthorizedException extends RuntimeException{

    public UserNotAuthorizedException() {
        super("User or password invalid");
    }

}
