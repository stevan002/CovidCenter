package com.example.euprava.Models;

import java.time.LocalDateTime;

public class Prijava {
    private Long id;
    private LocalDateTime vremeDobijanjaDoze;
    private Long pacijentId;
    private Pacijent pacijent;
    private Long vakcinaId;
    private Vakcina vakcina;

    public Prijava(Long id, LocalDateTime vremeDobijanjaDoze, Pacijent pacijent, Vakcina vakcina) {
        this.id = id;
        this.vremeDobijanjaDoze = vremeDobijanjaDoze;
        this.pacijent = pacijent;
        this.vakcina = vakcina;
    }

    public Prijava() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getVremeDobijanjaDoze() {
        return vremeDobijanjaDoze;
    }

    public void setVremeDobijanjaDoze(LocalDateTime vremeDobijanjaDoze) {
        this.vremeDobijanjaDoze = vremeDobijanjaDoze;
    }

    public Long getPacijentId() {
        return pacijentId;
    }

    public void setPacijentId(Long pacijentId) {
        this.pacijentId = pacijentId;
    }

    public Pacijent getPacijent() {
        return pacijent;
    }

    public void setPacijent(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    public Long getVakcinaId() {
        return vakcinaId;
    }

    public void setVakcinaId(Long vakcinaId) {
        this.vakcinaId = vakcinaId;
    }

    public Vakcina getVakcina() {
        return vakcina;
    }

    public void setVakcina(Vakcina vakcina) {
        this.vakcina = vakcina;
    }

    @Override
    public String toString() {
        return "Prijava{" +
                "id=" + id +
                ", vremeDobijanjaDoze=" + vremeDobijanjaDoze +
                ", pacijentId=" + pacijentId +
                ", pacijent=" + pacijent +
                ", vakcinaId=" + vakcinaId +
                ", vakcina=" + vakcina +
                '}';
    }
}
