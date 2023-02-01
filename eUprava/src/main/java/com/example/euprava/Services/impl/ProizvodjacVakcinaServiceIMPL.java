package com.example.euprava.Services.impl;

import com.example.euprava.Models.ProizvodjacVakcina;
import com.example.euprava.Services.ProizvodjacVakcinaService;
import com.example.euprava.dao.ProizvodjacVakcinaDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProizvodjacVakcinaServiceIMPL implements ProizvodjacVakcinaService{
    //@Autowired
    private ProizvodjacVakcinaDAO proizvodjacVakcinaDAO;

    @Override
    public ProizvodjacVakcina findOne(Long id) {
        return proizvodjacVakcinaDAO.findOne(id);
    }

    @Override
    public List<ProizvodjacVakcina> findAll() {
        return proizvodjacVakcinaDAO.findAll();
    }

    @Override
    public ProizvodjacVakcina save(ProizvodjacVakcina proizvodjacVakcina) {
        proizvodjacVakcinaDAO.save(proizvodjacVakcina);
        return proizvodjacVakcina;
    }

    @Override
    public ProizvodjacVakcina update(ProizvodjacVakcina proizvodjacVakcina) {
        proizvodjacVakcinaDAO.update(proizvodjacVakcina);
        return proizvodjacVakcina;
    }

    @Override
    public ProizvodjacVakcina delete(Long id) {
        ProizvodjacVakcina proizvodjacVakcina = findOne(id);
        if(proizvodjacVakcina != null){
            proizvodjacVakcinaDAO.delete(id);
        }
        return proizvodjacVakcina;
    }

    @Override
    public List<ProizvodjacVakcina> find(String proizvodjac) {
        if(proizvodjac != null){
            return proizvodjacVakcinaDAO.findAll();
        }
        return proizvodjacVakcinaDAO.find(proizvodjac);
    }
}
