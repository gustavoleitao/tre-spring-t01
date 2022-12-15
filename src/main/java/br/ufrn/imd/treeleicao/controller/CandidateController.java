package br.ufrn.imd.treeleicao.controller;

import br.ufrn.imd.treeleicao.model.Candidate;
import br.ufrn.imd.treeleicao.repository.CandidateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CandidateController {

    private final CandidateRepository repository;

    CandidateController(CandidateRepository repository) {
        this.repository = repository;
    }

    private final AtomicLong counter = new AtomicLong();

//    @GetMapping("/candidates")
//    public List<Candidate> find(){
//        return repository.findAll();
//    }

    @GetMapping("/candidates")
    public List<Candidate> findByName(@RequestParam(defaultValue = "") String name){
        if (name.isEmpty()){
            return repository.findAll();
        }else{
            return repository.findCandidateByNameOrderByName(name);
        }
    }

    @PostMapping("/candidate")
    Candidate newCandidate(@RequestBody Candidate newCandidate) {
        return repository.save(newCandidate);
    }

    @GetMapping("/candidate/{id}")
    Candidate one(@PathVariable String id){
        return repository.findById(UUID.fromString(id)).orElseThrow(() -> new CandidateNotFoundException(id));
    }

    @PutMapping("/candidate/{id}")
    Candidate update(@RequestBody Candidate newCandidate, @PathVariable String id){
        return repository.findById(UUID.fromString(id)).map(candidate -> {
            candidate.setName(newCandidate.getName());
            return repository.save(candidate);
        }).orElseGet(() -> {
            newCandidate.setId(UUID.fromString(id));
            return repository.save(newCandidate);
        });
    }

    @DeleteMapping("/candidate/{id}")
    void deleteCandidate(@PathVariable String id) {
        repository.deleteById(UUID.fromString(id));
    }

}
