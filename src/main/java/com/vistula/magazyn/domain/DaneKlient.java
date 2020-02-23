package com.vistula.magazyn.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DaneKlient.
 */
@Entity
@Table(name = "dane_klient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DaneKlient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "imie", nullable = false)
    private String imie;

    @NotNull
    @Column(name = "nazwisko", nullable = false)
    private String nazwisko;

    @NotNull
    @Column(name = "numer_telefonu", nullable = false)
    private Integer numerTelefonu;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "firma", nullable = false)
    private String firma;

    @NotNull
    @Column(name = "ulica", nullable = false)
    private String ulica;

    @NotNull
    @Column(name = "miejscowosc", nullable = false)
    private String miejscowosc;

    @NotNull
    @Column(name = "kod_pocztowy", nullable = false)
    private String kodPocztowy;

    @NotNull
    @Column(name = "kraj", nullable = false)
    private String kraj;

    @NotNull
    @Column(name = "nip", nullable = false)
    private Long nip;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public DaneKlient imie(String imie) {
        this.imie = imie;
        return this;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public DaneKlient nazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
        return this;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Integer getNumerTelefonu() {
        return numerTelefonu;
    }

    public DaneKlient numerTelefonu(Integer numerTelefonu) {
        this.numerTelefonu = numerTelefonu;
        return this;
    }

    public void setNumerTelefonu(Integer numerTelefonu) {
        this.numerTelefonu = numerTelefonu;
    }

    public String getEmail() {
        return email;
    }

    public DaneKlient email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirma() {
        return firma;
    }

    public DaneKlient firma(String firma) {
        this.firma = firma;
        return this;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getUlica() {
        return ulica;
    }

    public DaneKlient ulica(String ulica) {
        this.ulica = ulica;
        return this;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public DaneKlient miejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
        return this;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public DaneKlient kodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
        return this;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getKraj() {
        return kraj;
    }

    public DaneKlient kraj(String kraj) {
        this.kraj = kraj;
        return this;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public Long getNip() {
        return nip;
    }

    public DaneKlient nip(Long nip) {
        this.nip = nip;
        return this;
    }

    public void setNip(Long nip) {
        this.nip = nip;
    }

    public User getUser() {
        return user;
    }

    public DaneKlient user(User user) {
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
        if (!(o instanceof DaneKlient)) {
            return false;
        }
        return id != null && id.equals(((DaneKlient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DaneKlient{" +
            "id=" + getId() +
            ", imie='" + getImie() + "'" +
            ", nazwisko='" + getNazwisko() + "'" +
            ", numerTelefonu=" + getNumerTelefonu() +
            ", email='" + getEmail() + "'" +
            ", firma='" + getFirma() + "'" +
            ", ulica='" + getUlica() + "'" +
            ", miejscowosc='" + getMiejscowosc() + "'" +
            ", kodPocztowy='" + getKodPocztowy() + "'" +
            ", kraj='" + getKraj() + "'" +
            ", nip=" + getNip() +
            "}";
    }
}
