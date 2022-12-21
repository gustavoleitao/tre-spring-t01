package br.ufrn.imd.treeleicao;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CandidateService {

    private CandidateRepository repository;

    CandidateService(CandidateRepository repository) {
        this.repository = repository;
    }

    public Collection<CandidateDTO> all(){
        var modelMapper = new ModelMapper();
        return repository.findAll().stream()
                .map(u -> modelMapper.map(u, CandidateDTO.class))
                .collect(Collectors.toList());
    }

    public CandidateDTO saveOrUpdate(CandidateDTO newCandidate) {
        var modelMapper = new ModelMapper();
        var candidate = modelMapper.map(newCandidate, Candidate.class);
        var result = repository.save(candidate);
        return modelMapper.map(result, CandidateDTO.class);
    }

    public CandidateDTO findById(Long id) {
        var modelMapper = new ModelMapper();
        var result = repository.findById(id).orElseThrow(() -> new CandidateNotFoundException(id));
        return modelMapper.map(result, CandidateDTO.class);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
