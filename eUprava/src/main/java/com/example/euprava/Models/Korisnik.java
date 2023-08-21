package com.example.euprava.Models;

import java.time.LocalDateTime;
import java.util.Date;

public class Korisnik {
    private Long id;
    private String email;
    private String lozinka;
    private String ime;
    private String prezime;
    private Date datumRodjenja;
    private String jmbg;
    private String adresa;
    private String brTelefona;
    private LocalDateTime datumVremeRegistracije;
    private EUloga uloga;

    public Korisnik() {
        this.datumVremeRegistracije = LocalDateTime.now();
        this.uloga = EUloga.Pacijent;
    }


    public Korisnik(Long id, String email, String lozinka, String ime, String prezime, Date datumRodjenja, String jmbg, String adresa, String brTelefona, LocalDateTime datumVremeRegistracije, EUloga uloga) {
        this.id = id;
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.jmbg = jmbg;
        this.adresa = adresa;
        this.brTelefona = brTelefona;
        this.datumVremeRegistracije = datumVremeRegistracije;
        this.uloga = uloga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public java.sql.Date getDatumRodjenja() {
        return (java.sql.Date) datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getBrTelefona() {
        return brTelefona;
    }

    public void setBrTelefona(String brTelefona) {
        this.brTelefona = brTelefona;
    }

    public LocalDateTime getDatumVremeRegistracije() {
        return datumVremeRegistracije;
    }

    public void setDatumVremeRegistracije(LocalDateTime datumVremeRegistracije) {
        this.datumVremeRegistracije = datumVremeRegistracije;
    }

    public EUloga getUloga() {
        return uloga;
    }

    public void setUloga(EUloga uloga) {
        this.uloga = uloga;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", datumRodjenja=" + datumRodjenja +
                ", jmbg='" + jmbg + '\'' +
                ", adresa='" + adresa + '\'' +
                ", brTelefona='" + brTelefona + '\'' +
                ", datumVremeRegistracije=" + datumVremeRegistracije +
                ", uloga=" + uloga +
                '}';
    }
}
