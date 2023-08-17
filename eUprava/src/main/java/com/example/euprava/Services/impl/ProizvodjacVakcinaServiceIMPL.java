package com.example.euprava.Services.impl;

import com.example.euprava.Models.ProizvodjacVakcina;
import com.example.euprava.Services.ProizvodjacVakcinaService;
import com.example.euprava.dao.ProizvodjacVakcinaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProizvodjacVakcinaServiceIMPL implements ProizvodjacVakcinaService{
    @Autowired
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
    public void save(ProizvodjacVakcina proizvodjacVakcina) {
        if(proizvodjacVakcinaDAO.findOne(proizvodjacVakcina.getId()) != null){
            proizvodjacVakcinaDAO.update(proizvodjacVakcina);
        }else{
            proizvodjacVakcinaDAO.save(proizvodjacVakcina);
        }
    }

    @Override
    public void delete(Long id) {
        proizvodjacVakcinaDAO.delete(id);
    }

}
