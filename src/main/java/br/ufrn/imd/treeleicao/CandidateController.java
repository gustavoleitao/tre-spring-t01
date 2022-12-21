package br.ufrn.imd.treeleicao;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CandidateController {

    private final CandidateRepository repository;

    CandidateController(CandidateRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/candidates")
    public List<Candidate> find(){
        return repository.findAll();
    }

    @PostMapping("/candidate")
    Candidate newCandidate(@RequestBody Candidate newCandidate) {
        return repository.save(newCandidate);
    }

    @GetMapping("/candidate/{id}")
    Candidate one(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new CandidateNotFoundException(id));
    }

    @PutMapping("/candidate/{id}")
    Candidate update(@RequestBody Candidate newCandidate, @PathVariable Long id){
        return repository.findById(id).map(candidate -> {
            candidate.setName(newCandidate.getName());
            return repository.save(candidate);
        }).orElseGet(() -> {
            newCandidate.setId(id);
            return repository.save(newCandidate);
        });
    }

    @DeleteMapping("/candidate/{id}")
    void deleteCandidate(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
