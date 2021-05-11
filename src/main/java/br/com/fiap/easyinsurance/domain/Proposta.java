package br.com.fiap.easyinsurance.domain;

import br.com.fiap.easyinsurance.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Proposta.
 */
@Entity
@Table(name = "proposta")
public class Proposta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_proposta", nullable = false)
    private String numeroProposta;

    @NotNull
    @Column(name = "data_proposta", nullable = false)
    private LocalDate dataProposta;

    @NotNull
    @Column(name = "valor_proposta", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorProposta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "propostas" }, allowSetters = true)
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties(value = { "corretors" }, allowSetters = true)
    private Corretor corretor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "propostas" }, allowSetters = true)
    private Plano plano;

    @ManyToOne
    @JsonIgnoreProperties(value = { "propostas" }, allowSetters = true)
    private Seguradora seguradora;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proposta id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumeroProposta() {
        return this.numeroProposta;
    }

    public Proposta numeroProposta(String numeroProposta) {
        this.numeroProposta = numeroProposta;
        return this;
    }

    public void setNumeroProposta(String numeroProposta) {
        this.numeroProposta = numeroProposta;
    }

    public LocalDate getDataProposta() {
        return this.dataProposta;
    }

    public Proposta dataProposta(LocalDate dataProposta) {
        this.dataProposta = dataProposta;
        return this;
    }

    public void setDataProposta(LocalDate dataProposta) {
        this.dataProposta = dataProposta;
    }

    public BigDecimal getValorProposta() {
        return this.valorProposta;
    }

    public Proposta valorProposta(BigDecimal valorProposta) {
        this.valorProposta = valorProposta;
        return this;
    }

    public void setValorProposta(BigDecimal valorProposta) {
        this.valorProposta = valorProposta;
    }

    public Status getStatus() {
        return this.status;
    }

    public Proposta status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public Proposta cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Corretor getCorretor() {
        return this.corretor;
    }

    public Proposta corretor(Corretor corretor) {
        this.setCorretor(corretor);
        return this;
    }

    public void setCorretor(Corretor corretor) {
        this.corretor = corretor;
    }

    public Plano getPlano() {
        return this.plano;
    }

    public Proposta plano(Plano plano) {
        this.setPlano(plano);
        return this;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Seguradora getSeguradora() {
        return this.seguradora;
    }

    public Proposta seguradora(Seguradora seguradora) {
        this.setSeguradora(seguradora);
        return this;
    }

    public void setSeguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proposta)) {
            return false;
        }
        return id != null && id.equals(((Proposta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proposta{" +
            "id=" + getId() +
            ", numeroProposta='" + getNumeroProposta() + "'" +
            ", dataProposta='" + getDataProposta() + "'" +
            ", valorProposta=" + getValorProposta() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
