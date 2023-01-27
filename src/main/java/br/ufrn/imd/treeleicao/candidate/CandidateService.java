package br.ufrn.imd.treeleicao.candidate;

import br.ufrn.imd.treeleicao.common.AbstractService;
import br.ufrn.imd.treeleicao.common.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CandidateService extends AbstractService<Candidate, CandidateDTO> {

    public CandidateService(CandidateRepository repository) {
        super(repository);
    }

    @Override
    public Class<Candidate> getEntityClass() {
        return Candidate.class;
    }

    @Override
    public Class<CandidateDTO> getDTOClass() {
        return CandidateDTO.class;
    }

    @Override
    public String getEntityName() {
        return "Candidate";
    }

}
