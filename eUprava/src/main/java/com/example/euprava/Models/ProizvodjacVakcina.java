package com.example.euprava.Models;

import java.util.List;

public class ProizvodjacVakcina {
    private Long id;
    private String proizvodjac;
    private String drzavaProizvodnje;

    public ProizvodjacVakcina() {
    }

    public ProizvodjacVakcina(Long id, String proizvodjac, String drzavaProizvodnje) {
        this.id = id;
        this.proizvodjac = proizvodjac;
        this.drzavaProizvodnje = drzavaProizvodnje;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public String getDrzavaProizvodnje() {
        return drzavaProizvodnje;
    }

    public void setDrzavaProizvodnje(String drzavaProizvodnje) {
        this.drzavaProizvodnje = drzavaProizvodnje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProizvodjacVakcina{" +
                "proizvodjac='" + proizvodjac + '\'' +
                ", drzavaProizvodnje='" + drzavaProizvodnje + '\'' +
                '}';
    }
}
