package com.example.euprava.Models;

import java.time.LocalDateTime;
import java.util.Date;

public class Vest {
    private Long id;
    private String naziv;
    private String sadrzaj;
    private LocalDateTime datumVremeObjavljivanja;

    public Vest() {
        this.datumVremeObjavljivanja = LocalDateTime.now();
    }

    public Vest(Long id, String naziv, String sadrzaj, LocalDateTime datumVremeObjavljivanja) {
        this.id = id;
        this.naziv = naziv;
        this.sadrzaj = sadrzaj;
        this.datumVremeObjavljivanja = datumVremeObjavljivanja;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public LocalDateTime getDatumVremeObjavljivanja() {
        return datumVremeObjavljivanja;
    }

    public void setDatumVremeObjavljivanja(LocalDateTime datumVremeObjavljivanja) {
        this.datumVremeObjavljivanja = datumVremeObjavljivanja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Vest{" +
                "naziv='" + naziv + '\'' +
                ", sadrzaj='" + sadrzaj + '\'' +
                ", datumVremeObjavljivanja=" + datumVremeObjavljivanja +
                '}';
    }
}
