package br.ufrn.imd.treeleicao.user;

import br.ufrn.imd.treeleicao.common.Page;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public Page<UserDTO> find(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "10") Integer size,
                              @RequestParam(defaultValue = "id") String sortBy){
        return service.all(page, size, sortBy);
    }

    @PostMapping("/user")
    UserDTO newCandidate(@RequestBody UserDTO entity) {
        return service.saveOrUpdate(entity);
    }

    @GetMapping("/user/{id}")
    UserDTO one(@PathVariable Long id){
        return service.findById(id);
    }

    @PutMapping("/user/{id}")
    UserDTO update(@RequestBody UserDTO entity, @PathVariable Long id){
        entity.setId(id);
        return service.saveOrUpdate(entity);
    }

    @DeleteMapping("/user/{id}")
    void deleteCandidate(@PathVariable Long id) {
        service.deleteById(id);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<String> onJdbcSQLIntegrityConstraintViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity("Os dados fornecidos não obedecem às restrições. " +
                "Provavelmente já existe um registro similar salvo no banco de dados.", HttpStatus.BAD_REQUEST);
    }

}
