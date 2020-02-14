package com.vistula.magazyn.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.vistula.magazyn.domain.enumeration.StatusZamowienieEnum;

/**
 * A Zamowienie.
 */
@Entity
@Table(name = "zamowienie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Zamowienie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cena")
    private Double cena;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusZamowienieEnum status;

    @Column(name = "data_utworzenia")
    private LocalDate dataUtworzenia;

    @ManyToOne
    @JsonIgnoreProperties("zamowienies")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCena() {
        return cena;
    }

    public Zamowienie cena(Double cena) {
        this.cena = cena;
        return this;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public StatusZamowienieEnum getStatus() {
        return status;
    }

    public Zamowienie status(StatusZamowienieEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusZamowienieEnum status) {
        this.status = status;
    }

    public LocalDate getDataUtworzenia() {
        return dataUtworzenia;
    }

    public Zamowienie dataUtworzenia(LocalDate dataUtworzenia) {
        this.dataUtworzenia = dataUtworzenia;
        return this;
    }

    public void setDataUtworzenia(LocalDate dataUtworzenia) {
        this.dataUtworzenia = dataUtworzenia;
    }

    public User getUser() {
        return user;
    }

    public Zamowienie user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zamowienie)) {
            return false;
        }
        return id != null && id.equals(((Zamowienie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Zamowienie{" +
            "id=" + getId() +
            ", cena=" + getCena() +
            ", status='" + getStatus() + "'" +
            ", dataUtworzenia='" + getDataUtworzenia() + "'" +
            "}";
    }
}
