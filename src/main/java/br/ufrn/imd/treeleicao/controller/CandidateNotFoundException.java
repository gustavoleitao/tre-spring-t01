package br.ufrn.imd.treeleicao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CandidateNotFoundException extends RuntimeException {

    CandidateNotFoundException(String id){
        super("Cound not find candidate "+id);
    }

}
