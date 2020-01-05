package com.vistula.magazyn.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.vistula.magazyn.domain.enumeration.StatusEnum;

import com.vistula.magazyn.domain.enumeration.StatusZamowieniaEnum;

/**
 * A ZamowienieWpis.
 */
@Entity
@Table(name = "zam_wpis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ZamowienieWpis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ilosc")
    private Integer ilosc;

    @Column(name = "cena")
    private Double cena;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_zamowienia")
    private StatusZamowieniaEnum statusZamowienia;

    @ManyToOne
    @JsonIgnoreProperties("zamowienieWpis")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("zamowienieWpis")
    private Produkt produkt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIlosc() {
        return ilosc;
    }

    public ZamowienieWpis ilosc(Integer ilosc) {
        this.ilosc = ilosc;
        return this;
    }

    public void setIlosc(Integer ilosc) {
        this.ilosc = ilosc;
    }

    public Double getCena() {
        return cena;
    }

    public ZamowienieWpis cena(Double cena) {
        this.cena = cena;
        return this;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public ZamowienieWpis status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public StatusZamowieniaEnum getStatusZamowienia() {
        return statusZamowienia;
    }

    public ZamowienieWpis statusZamowienia(StatusZamowieniaEnum statusZamowienia) {
        this.statusZamowienia = statusZamowienia;
        return this;
    }

    public void setStatusZamowienia(StatusZamowieniaEnum statusZamowienia) {
        this.statusZamowienia = statusZamowienia;
    }

    public User getUser() {
        return user;
    }

    public ZamowienieWpis user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public ZamowienieWpis produkt(Produkt produkt) {
        this.produkt = produkt;
        return this;
    }

    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ZamowienieWpis)) {
            return false;
        }
        return id != null && id.equals(((ZamowienieWpis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ZamowienieWpis{" +
            "id=" + getId() +
            ", ilosc=" + getIlosc() +
            ", cena=" + getCena() +
            ", status='" + getStatus() + "'" +
            ", statusZamowienia='" + getStatusZamowienia() + "'" +
            "}";
    }
}
