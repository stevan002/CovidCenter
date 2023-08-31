package com.example.euprava.Services;

import com.example.euprava.Models.Prijava;

import java.time.LocalDateTime;
import java.util.List;

public interface PrijavaService {

    List<Prijava> findAll();
    List<Prijava> search(String pretraga);
    Prijava findOne(Long id);
    boolean isPast(Integer minuti, LocalDateTime datumVreme);
    void save(Prijava prijava);
    void update(Prijava prijava);
    void deleteByPacijent(Long pacijentId, Long id);
    void delete(Long id);
}
