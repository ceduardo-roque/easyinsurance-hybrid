package br.com.fiap.easyinsurance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Corretor.
 */
@Entity
@Table(name = "corretor")
public class Corretor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome_corretor", nullable = false)
    private String nomeCorretor;

    @NotNull
    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "telefone")
    private String telefone;

    @OneToMany(mappedBy = "corretor")
    @JsonIgnoreProperties(value = { "cliente", "corretor", "plano", "seguradora" }, allowSetters = true)
    private Set<Proposta> corretors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Corretor id(Long id) {
        this.id = id;
        return this;
    }

    public String getNomeCorretor() {
        return this.nomeCorretor;
    }

    public Corretor nomeCorretor(String nomeCorretor) {
        this.nomeCorretor = nomeCorretor;
        return this;
    }

    public void setNomeCorretor(String nomeCorretor) {
        this.nomeCorretor = nomeCorretor;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Corretor usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Corretor dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public Corretor foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return this.fotoContentType;
    }

    public Corretor fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Corretor telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<Proposta> getCorretors() {
        return this.corretors;
    }

    public Corretor corretors(Set<Proposta> propostas) {
        this.setCorretors(propostas);
        return this;
    }

    public Corretor addCorretor(Proposta proposta) {
        this.corretors.add(proposta);
        proposta.setCorretor(this);
        return this;
    }

    public Corretor removeCorretor(Proposta proposta) {
        this.corretors.remove(proposta);
        proposta.setCorretor(null);
        return this;
    }

    public void setCorretors(Set<Proposta> propostas) {
        if (this.corretors != null) {
            this.corretors.forEach(i -> i.setCorretor(null));
        }
        if (propostas != null) {
            propostas.forEach(i -> i.setCorretor(this));
        }
        this.corretors = propostas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Corretor)) {
            return false;
        }
        return id != null && id.equals(((Corretor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Corretor{" +
            "id=" + getId() +
            ", nomeCorretor='" + getNomeCorretor() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
