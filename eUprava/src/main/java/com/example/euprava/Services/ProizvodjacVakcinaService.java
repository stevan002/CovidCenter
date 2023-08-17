package com.example.euprava.Services;

import com.example.euprava.Models.ProizvodjacVakcina;

import java.util.List;

public interface ProizvodjacVakcinaService {
    ProizvodjacVakcina findOne(Long id);
    List<ProizvodjacVakcina> findAll();
    void save(ProizvodjacVakcina proizvodjacVakcina);
    void delete(Long id);
}
