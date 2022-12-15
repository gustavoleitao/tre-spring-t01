package br.ufrn.imd.treeleicao;

public class CandidateNotFoundException extends RuntimeException {
    public CandidateNotFoundException(Long id) {
        super(String.format("Candidate %d not found", id));
    }
}
