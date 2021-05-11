package br.com.fiap.easyinsurance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Plano.
 */
@Entity
@Table(name = "plano")
public class Plano implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome_plano", nullable = false)
    private String nomePlano;

    @OneToMany(mappedBy = "plano")
    @JsonIgnoreProperties(value = { "cliente", "corretor", "plano", "seguradora" }, allowSetters = true)
    private Set<Proposta> propostas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plano id(Long id) {
        this.id = id;
        return this;
    }

    public String getNomePlano() {
        return this.nomePlano;
    }

    public Plano nomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
        return this;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    public Set<Proposta> getPropostas() {
        return this.propostas;
    }

    public Plano propostas(Set<Proposta> propostas) {
        this.setPropostas(propostas);
        return this;
    }

    public Plano addProposta(Proposta proposta) {
        this.propostas.add(proposta);
        proposta.setPlano(this);
        return this;
    }

    public Plano removeProposta(Proposta proposta) {
        this.propostas.remove(proposta);
        proposta.setPlano(null);
        return this;
    }

    public void setPropostas(Set<Proposta> propostas) {
        if (this.propostas != null) {
            this.propostas.forEach(i -> i.setPlano(null));
        }
        if (propostas != null) {
            propostas.forEach(i -> i.setPlano(this));
        }
        this.propostas = propostas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plano)) {
            return false;
        }
        return id != null && id.equals(((Plano) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plano{" +
            "id=" + getId() +
            ", nomePlano='" + getNomePlano() + "'" +
            "}";
    }
}
