package com.example.euprava.dao;

import com.example.euprava.Models.Korisnik;

import java.util.List;

public interface KorisnikDAO {
    public Korisnik findOne(Long id);
    Korisnik findOneByEmail(String email);
    public Korisnik findOneByEmailAndPassword(String email, String lozinka);
    public List<Korisnik> findAll();
    public int save(Korisnik korisnik);
    public int update(Korisnik korisnik);
    public int delete(Long id);
}
