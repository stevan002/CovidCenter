package com.example.euprava.dao;

import com.example.euprava.Models.ProizvodjacVakcina;

import java.util.List;

public interface ProizvodjacVakcinaDAO {
    public ProizvodjacVakcina findOne(Long id);
    public List<ProizvodjacVakcina> findAll();
    public List<ProizvodjacVakcina> find(String proizvodjac);
    public int save(ProizvodjacVakcina proizvodjacVakcina);
    public int update(ProizvodjacVakcina proizvodjacVakcina);
    public int delete(Long id);

}
