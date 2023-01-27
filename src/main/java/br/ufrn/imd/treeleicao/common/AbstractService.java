package br.ufrn.imd.treeleicao.common;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractService<E,D> {

    protected JpaRepository<E,Long> repository;

    public AbstractService(JpaRepository<E, Long> repository) {
        this.repository = repository;
    }

    public abstract Class<E> getEntityClass();

    public abstract Class<D> getDTOClass();

    public abstract String getEntityName();

    public Collection<D> all(){
        var modelMapper = new ModelMapper();
        return repository.findAll().stream()
                .map(u -> modelMapper.map(u, getDTOClass()))
                .collect(Collectors.toList());
    }

    public Page<D> all(int page, int pageSize){
        Pageable paging = PageRequest.of(page, pageSize);
        return findAllPeageble(paging);
    }

    public Page<D> all(int page, int pageSize, String ...sortBy){
        Sort.by("email").ascending().and(Sort.by("username").descending());
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
        return findAllPeageble(paging);
    }

    private Page findAllPeageble(Pageable paging) {
        var pageable = repository.findAll(paging);
        var modelMapper = new ModelMapper();
        List<D> itens = pageable.stream()
                .peek(System.out::println)
                .map(u -> modelMapper.map(u, getDTOClass()))
                .collect(Collectors.toList());
        var actualPage = pageable.getPageable();
        return Page.from(actualPage.getPageNumber(), actualPage.getPageSize(), pageable.getTotalElements(), itens);
    }

    public D saveOrUpdate(D newCandidate) {
        var modelMapper = new ModelMapper();
        var candidate = modelMapper.map(newCandidate, getEntityClass());
        var result = repository.save(candidate);
        return modelMapper.map(result, getDTOClass());
    }

    public D findById(Long id) {
        var modelMapper = new ModelMapper();
        var result = repository.findById(id).orElseThrow(() -> new NotFoundException(getEntityName(), id));
        return modelMapper.map(result, getDTOClass());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
