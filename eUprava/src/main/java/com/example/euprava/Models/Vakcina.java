package com.example.euprava.Models;

public class Vakcina {
    private Long id;
    private String ime;
    private int kolicina;
    private ProizvodjacVakcina proizvodjac;
    private Long proizvodjacId;

    public Vakcina() {
    }

    public Vakcina(Long id, String ime, int kolicina, ProizvodjacVakcina proizvodjac) {
        this.id = id;
        this.ime = ime;
        this.kolicina = kolicina;
        this.proizvodjac = proizvodjac;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public ProizvodjacVakcina getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(ProizvodjacVakcina proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public Long getProizvodjacId() {
        return proizvodjacId;
    }

    public void setProizvodjacId(Long proizvodjacId) {
        this.proizvodjacId = proizvodjacId;
    }

    @Override
    public String toString() {
        return "Vakcina{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", kolicina=" + kolicina +
                ", proizvodjac=" + proizvodjac +
                '}';
    }
}
