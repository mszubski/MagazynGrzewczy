package com.vistula.magazyn.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.vistula.magazyn.domain.enumeration.StatusProdukt;

import com.vistula.magazyn.domain.enumeration.ProduktKategoriaEnum;

/**
 * A Produkt.
 */
@Entity
@Table(name = "produkt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Produkt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nazwa")
    private String nazwa;

    @Column(name = "cena")
    private Double cena;

    @Column(name = "opis")
    private String opis;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProdukt status;

    @Column(name = "zdjecie")
    private String zdjecie;

    @Column(name = "stan")
    private Integer stan;

    @Enumerated(EnumType.STRING)
    @Column(name = "kategoria")
    private ProduktKategoriaEnum kategoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Produkt nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Double getCena() {
        return cena;
    }

    public Produkt cena(Double cena) {
        this.cena = cena;
        return this;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public Produkt opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public StatusProdukt getStatus() {
        return status;
    }

    public Produkt status(StatusProdukt status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusProdukt status) {
        this.status = status;
    }

    public String getZdjecie() {
        return zdjecie;
    }

    public Produkt zdjecie(String zdjecie) {
        this.zdjecie = zdjecie;
        return this;
    }

    public void setZdjecie(String zdjecie) {
        this.zdjecie = zdjecie;
    }

    public Integer getStan() {
        return stan;
    }

    public Produkt stan(Integer stan) {
        this.stan = stan;
        return this;
    }

    public void setStan(Integer stan) {
        this.stan = stan;
    }

    public ProduktKategoriaEnum getKategoria() {
        return kategoria;
    }

    public Produkt kategoria(ProduktKategoriaEnum kategoria) {
        this.kategoria = kategoria;
        return this;
    }

    public void setKategoria(ProduktKategoriaEnum kategoria) {
        this.kategoria = kategoria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produkt)) {
            return false;
        }
        return id != null && id.equals(((Produkt) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produkt{" +
            "id=" + getId() +
            ", nazwa='" + getNazwa() + "'" +
            ", cena=" + getCena() +
            ", opis='" + getOpis() + "'" +
            ", status='" + getStatus() + "'" +
            ", zdjecie='" + getZdjecie() + "'" +
            ", stan=" + getStan() +
            ", kategoria='" + getKategoria() + "'" +
            "}";
    }
}
