package com.example.euprava.Models;

import java.time.LocalDateTime;

public class Pacijent {

    private Long korisnikId;
    private Integer doze;
    private LocalDateTime vremePrimanjaDoze;
    private Korisnik korisnik;

    public Pacijent(Korisnik noviKorisnik) {
        doze = 0;
        vremePrimanjaDoze = null;
        this.korisnik = noviKorisnik;
        this.korisnikId = noviKorisnik.getId();
    }

    public Pacijent(Long korisnikId, Integer doze, LocalDateTime vremePrimanjaDoze, Korisnik korisnik) {
        this.korisnikId = korisnikId;
        this.doze = doze;
        this.vremePrimanjaDoze = vremePrimanjaDoze;
        this.korisnik = korisnik;
    }

    public Pacijent() {
    }

    public Long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Integer getDoze() {
        return doze;
    }

    public void setDoze(Integer doze) {
        this.doze = doze;
    }

    public LocalDateTime getVremePrimanjaDoze() {
        return vremePrimanjaDoze;
    }

    public void setVremePrimanjaDoze(LocalDateTime vremePrimanjaDoze) {
        this.vremePrimanjaDoze = vremePrimanjaDoze;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public String toString() {
        return "Pacijent{" +
                "korisnikId=" + korisnikId +
                ", doze=" + doze +
                ", vremePrimanjaDoze=" + vremePrimanjaDoze +
                ", korisnik=" + korisnik +
                '}';
    }
}
