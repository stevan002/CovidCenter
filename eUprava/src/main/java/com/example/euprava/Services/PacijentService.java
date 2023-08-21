package com.example.euprava.Services;

import com.example.euprava.Models.Pacijent;

import java.util.List;

public interface PacijentService {

    Pacijent findOne(Long id);
    List<Pacijent> listAll();
    void save(Pacijent pacijent);
    void update(Pacijent pacijent);
    void delete(Long id);


}
