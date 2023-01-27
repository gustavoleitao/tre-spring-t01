package br.ufrn.imd.treeleicao.candidate;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class CandidateController {

    private final CandidateService service;

    CandidateController(CandidateService service) {
        this.service = service;
    }

    @GetMapping("/candidates")
    public Collection<CandidateDTO> find(){
        return service.all();
    }

    @PostMapping("/candidate")
    CandidateDTO newCandidate(@RequestBody CandidateDTO newCandidate) {
        return service.saveOrUpdate(newCandidate);
    }

    @GetMapping("/candidate/{id}")
    CandidateDTO one(@PathVariable Long id){
        return service.findById(id);
    }

    @PutMapping("/candidate/{id}")
    CandidateDTO update(@RequestBody CandidateDTO newCandidate, @PathVariable Long id){
        newCandidate.setId(id);
        return service.saveOrUpdate(newCandidate);
    }

    @DeleteMapping("/candidate/{id}")
    void deleteCandidate(@PathVariable Long id) {
        service.deleteById(id);
    }

}


