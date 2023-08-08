package com.example.euprava.Services.impl;

import com.example.euprava.Models.Vakcina;
import com.example.euprava.Services.VakcinaService;
import com.example.euprava.dao.VakcinaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VakcinaServiceIMPL implements VakcinaService {
    @Autowired
    private VakcinaDAO vakcinaDAO;


    @Override
    public Vakcina findOne(Long id) {
        return vakcinaDAO.findOne(id);
    }

    @Override
    public Vakcina findOneByNaziv(String nazivProizvodjaca) {
        return vakcinaDAO.findOneByNaziv(nazivProizvodjaca);
    }

    @Override
    public Vakcina findOneByDrzava(String drzavaProizvodnje) {
        return vakcinaDAO.findOneByDrzava(drzavaProizvodnje);
    }

    @Override
    public List<Vakcina> findByKolicina(int min, int max) {
        return vakcinaDAO.findOneByKolicina(min, max);
    }

    @Override
    public List<Vakcina> findAll() {
        return vakcinaDAO.findAll();
    }

    @Override
    public Vakcina save(Vakcina vakcina) {
        vakcinaDAO.save(vakcina);
        return vakcina;
    }

    @Override
    public Vakcina update(Vakcina vakcina) {
        vakcinaDAO.update(vakcina);
        return vakcina;
    }

    @Override
    public Vakcina delete(Long id) {
        Vakcina vakcina = vakcinaDAO.findOne(id);
        vakcinaDAO.delete(id);
        return vakcina;
    }
}
