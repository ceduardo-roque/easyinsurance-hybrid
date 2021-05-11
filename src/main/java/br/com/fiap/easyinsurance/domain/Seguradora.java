package br.com.fiap.easyinsurance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Seguradora.
 */
@Entity
@Table(name = "seguradora")
public class Seguradora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome_seguradora", nullable = false)
    private String nomeSeguradora;

    @OneToMany(mappedBy = "seguradora")
    @JsonIgnoreProperties(value = { "cliente", "corretor", "plano", "seguradora" }, allowSetters = true)
    private Set<Proposta> propostas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seguradora id(Long id) {
        this.id = id;
        return this;
    }

    public String getNomeSeguradora() {
        return this.nomeSeguradora;
    }

    public Seguradora nomeSeguradora(String nomeSeguradora) {
        this.nomeSeguradora = nomeSeguradora;
        return this;
    }

    public void setNomeSeguradora(String nomeSeguradora) {
        this.nomeSeguradora = nomeSeguradora;
    }

    public Set<Proposta> getPropostas() {
        return this.propostas;
    }

    public Seguradora propostas(Set<Proposta> propostas) {
        this.setPropostas(propostas);
        return this;
    }

    public Seguradora addProposta(Proposta proposta) {
        this.propostas.add(proposta);
        proposta.setSeguradora(this);
        return this;
    }

    public Seguradora removeProposta(Proposta proposta) {
        this.propostas.remove(proposta);
        proposta.setSeguradora(null);
        return this;
    }

    public void setPropostas(Set<Proposta> propostas) {
        if (this.propostas != null) {
            this.propostas.forEach(i -> i.setSeguradora(null));
        }
        if (propostas != null) {
            propostas.forEach(i -> i.setSeguradora(this));
        }
        this.propostas = propostas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seguradora)) {
            return false;
        }
        return id != null && id.equals(((Seguradora) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Seguradora{" +
            "id=" + getId() +
            ", nomeSeguradora='" + getNomeSeguradora() + "'" +
            "}";
    }
}
