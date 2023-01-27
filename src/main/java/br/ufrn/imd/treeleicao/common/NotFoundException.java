package br.ufrn.imd.treeleicao.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, Long id) {
        super(String.format("%s %d not found", entity, id));
    }
}
