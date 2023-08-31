package com.example.euprava.Services;

import com.example.euprava.Models.Vakcina;

import java.util.List;

public interface VakcinaService {
    Vakcina findOne(Long id);
    List<Vakcina> findAll();
    List<Vakcina> search(String pretraga);
    List<Vakcina> searchKolicina(Integer min, Integer max);
    List<Vakcina> sortiranje(String sort, String direction);
    void save(Vakcina vakcina);
    Vakcina delete(Long id);
}
