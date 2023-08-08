package com.example.euprava.Models;

import java.time.LocalDateTime;

public class VestObolelima {
    private Long id;
    private int brObolelih;
    private int brTestiranih;
    private int brUkupnoObolelih;
    private int brHospitalizovanih;
    private int brNaRespiratorima;
    private LocalDateTime datumVremeObjavljivanja;

    public VestObolelima() {
        this.datumVremeObjavljivanja = LocalDateTime.now();
    }

    public VestObolelima(Long id, int brObolelih, int brTestiranih, int brUkupnoObolelih, int brHospitalizovanih, int brNaRespiratorima, LocalDateTime datumVremeObjavljivanja) {
        this.id = id;
        this.brObolelih = brObolelih;
        this.brTestiranih = brTestiranih;
        this.brUkupnoObolelih = brUkupnoObolelih;
        this.brHospitalizovanih = brHospitalizovanih;
        this.brNaRespiratorima = brNaRespiratorima;
        this.datumVremeObjavljivanja = datumVremeObjavljivanja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBrObolelih() {
        return brObolelih;
    }

    public void setBrObolelih(int brObolelih) {
        this.brObolelih = brObolelih;
    }

    public int getBrTestiranih() {
        return brTestiranih;
    }

    public void setBrTestiranih(int brTestiranih) {
        this.brTestiranih = brTestiranih;
    }

    public int getBrUkupnoObolelih() {
        return brUkupnoObolelih;
    }

    public void setBrUkupnoObolelih(int brUkupnoObolelih) {
        this.brUkupnoObolelih = brUkupnoObolelih;
    }

    public int getBrHospitalizovanih() {
        return brHospitalizovanih;
    }

    public void setBrHospitalizovanih(int brHospitalizovanih) {
        this.brHospitalizovanih = brHospitalizovanih;
    }

    public int getBrNaRespiratorima() {
        return brNaRespiratorima;
    }

    public void setBrNaRespiratorima(int brNaRespiratorima) {
        this.brNaRespiratorima = brNaRespiratorima;
    }

    public LocalDateTime getDatumVremeObjavljivanja() {
        return datumVremeObjavljivanja;
    }

    public void setDatumVremeObjavljivanja(LocalDateTime datumVremeObjavljivanja) {
        this.datumVremeObjavljivanja = datumVremeObjavljivanja;
    }

    @Override
    public String toString() {
        return "VestObolelima{" +
                "brObolelih=" + brObolelih +
                ", brTestiranih=" + brTestiranih +
                ", brUkupnoObolelih=" + brUkupnoObolelih +
                ", brHospitalizovanih=" + brHospitalizovanih +
                ", brNaRespiratorima=" + brNaRespiratorima +
                ", datumVremeObjavljivanja=" + datumVremeObjavljivanja +
                '}';
    }
}
