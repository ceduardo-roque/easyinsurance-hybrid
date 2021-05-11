package br.com.fiap.easyinsurance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome_cliente", nullable = false)
    private String nomeCliente;

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

    @OneToMany(mappedBy = "cliente")
    @JsonIgnoreProperties(value = { "cliente", "corretor", "plano", "seguradora" }, allowSetters = true)
    private Set<Proposta> propostas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente id(Long id) {
        this.id = id;
        return this;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public Cliente nomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
        return this;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Cliente usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Cliente dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public Cliente foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return this.fotoContentType;
    }

    public Cliente fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Cliente telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<Proposta> getPropostas() {
        return this.propostas;
    }

    public Cliente propostas(Set<Proposta> propostas) {
        this.setPropostas(propostas);
        return this;
    }

    public Cliente addProposta(Proposta proposta) {
        this.propostas.add(proposta);
        proposta.setCliente(this);
        return this;
    }

    public Cliente removeProposta(Proposta proposta) {
        this.propostas.remove(proposta);
        proposta.setCliente(null);
        return this;
    }

    public void setPropostas(Set<Proposta> propostas) {
        if (this.propostas != null) {
            this.propostas.forEach(i -> i.setCliente(null));
        }
        if (propostas != null) {
            propostas.forEach(i -> i.setCliente(this));
        }
        this.propostas = propostas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nomeCliente='" + getNomeCliente() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
