package com.example.euprava.dao;

import com.example.euprava.Models.ProizvodjacVakcina;
import com.example.euprava.Models.Vakcina;

import java.util.List;

public interface VakcinaDAO {
    public Vakcina findOne(Long id);
    public List<Vakcina> search(String pretraga);
    public List<Vakcina> pretragaPoUnetojKolicini(Integer min, Integer max);
    public List<Vakcina> sortiranje(String sort, String direction);
    public List<Vakcina> findAll();
    public int save(Vakcina vakcina);
    public int update(Vakcina vakcina);
    public int delete(Long id); //moze koristiti int ili boolean
}
