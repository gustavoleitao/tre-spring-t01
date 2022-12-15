package br.ufrn.imd.treeleicao.repository;


import br.ufrn.imd.treeleicao.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    List<Candidate> findCandidateByNameOrderByName(String name);

    List<Candidate> findCandidateByNameContaining(String name);

}
