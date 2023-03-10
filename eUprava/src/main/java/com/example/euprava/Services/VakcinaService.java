package com.example.euprava.Services;

import com.example.euprava.Models.Vakcina;

import java.util.List;

public interface VakcinaService {
    Vakcina findOne(Long id);
    Vakcina findOneByNaziv(String nazivProizvodjaca);
    Vakcina findOneByDrzava(String drzavaProizvodnje);
    List<Vakcina> findAll();
    Vakcina save(Vakcina vakcina);
    Vakcina update(Vakcina vakcina);
    Vakcina delete(Long id);
}
