package com.example.euprava.dao;

import com.example.euprava.Models.Pacijent;

import java.util.List;

public interface PacijentDAO {
    Pacijent findOne(Long id);
    List<Pacijent> listAll();
    int save(Pacijent pacijent);
    int update(Pacijent pacijent);
    int delete(Long id);
}
