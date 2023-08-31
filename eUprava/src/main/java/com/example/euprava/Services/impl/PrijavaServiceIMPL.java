package com.example.euprava.Services.impl;

import com.example.euprava.Models.Prijava;
import com.example.euprava.Services.PrijavaService;
import com.example.euprava.dao.PrijavaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrijavaServiceIMPL implements PrijavaService {

    @Autowired
    private PrijavaDAO prijavaDAO;
    @Override
    public List<Prijava> findAll() {
        return prijavaDAO.findAll();
    }

    @Override
    public List<Prijava> search(String pretraga) {
        return prijavaDAO.search(pretraga);
    }

    @Override
    public Prijava findOne(Long id) {
        return prijavaDAO.findOne(id);
    }

    @Override
    public boolean isPast(Integer minuti, LocalDateTime datumVreme) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(datumVreme.plusMinutes(minuti));
    }

    @Override
    public void save(Prijava prijava) {
        prijavaDAO.save(prijava);
    }

    @Override
    public void update(Prijava prijava) {
        prijavaDAO.update(prijava);
    }

    @Override
    public void deleteByPacijent(Long pacijentId) {
        prijavaDAO.deleteByPacijent(pacijentId);
    }

    @Override
    public void delete(Long id) {
        prijavaDAO.delete(id);
    }
}
