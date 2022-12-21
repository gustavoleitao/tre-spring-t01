package br.ufrn.imd.treeleicao;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Temporal(value=TemporalType.DATE)
    private Date dataValidade;

    public Candidate() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
