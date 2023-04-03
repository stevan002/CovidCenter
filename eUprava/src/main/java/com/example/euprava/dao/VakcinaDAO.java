package com.example.euprava.dao;

import com.example.euprava.Models.ProizvodjacVakcina;
import com.example.euprava.Models.Vakcina;

import java.util.List;

public interface VakcinaDAO {
    public Vakcina findOne(Long id);
    public Vakcina findOneByNaziv(String nazivProizvodjaca);
    public Vakcina findOneByDrzava(String drzavaProizvodnje);
    public List<Vakcina> findOneByKolicina(int minKolicina, int maxKolicina);
    public List<Vakcina> sortAll(String sort);
    public List<Vakcina> findAll();
    public int save(Vakcina vakcina);
    public int update(Vakcina vakcina);
    public int delete(Long id); //moze koristiti int ili boolean
}
