package com.example.euprava.Services;

import com.example.euprava.Models.ProizvodjacVakcina;

import java.util.List;

public interface ProizvodjacVakcinaService {
    ProizvodjacVakcina findOne(Long id);
    List<ProizvodjacVakcina> findAll();
    ProizvodjacVakcina save(ProizvodjacVakcina proizvodjacVakcina);
    ProizvodjacVakcina update(ProizvodjacVakcina proizvodjacVakcina);
    ProizvodjacVakcina delete(Long id);
}
