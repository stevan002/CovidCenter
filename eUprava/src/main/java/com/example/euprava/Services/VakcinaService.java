package com.example.euprava.Services;

import com.example.euprava.Models.Vakcina;

import java.util.List;

public interface VakcinaService {
    Vakcina findOne(Long id);
    List<Vakcina> findAll();
    Vakcina save(Vakcina vakcina);
    Vakcina delete(Long id);
}
