package com.example.euprava.dao;

import com.example.euprava.Models.Prijava;

import java.util.List;

public interface PrijavaDAO {
    List<Prijava> findAll();
    List<Prijava> search(String pretraga);
    Prijava findOne(Long id);
    int save(Prijava prijava);
    int update(Prijava prijava);
    void deleteByPacijent(Long pacijentId, Long id);
    int delete(Long id);
}
