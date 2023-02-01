package com.example.euprava.Services.impl;

import com.example.euprava.Models.Korisnik;
import com.example.euprava.Services.KorisnikService;
import com.example.euprava.dao.KorisnikDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KorisnikServiceIMPL implements KorisnikService {
    @Autowired
    private KorisnikDAO korisnikDAO;
    @Override
    public Korisnik findOne(Long id) {
        return korisnikDAO.findOne(id);
    }
    @Override
    public Korisnik findOne(String email) {
        return korisnikDAO.findOne(email);
    }

    @Override
    public Korisnik findOne(String email, String lozinka) {
        return korisnikDAO.findOne(email, lozinka);
    }

    @Override
    public List<Korisnik> findAll() {
        return korisnikDAO.findAll();
    }

    @Override
    public Korisnik save(Korisnik korisnik) {
        korisnikDAO.save(korisnik);
        return korisnik;
    }


    @Override
    public Korisnik update(Korisnik korisnik) {
        korisnikDAO.update(korisnik);
        return korisnik;
    }


    @Override
    public Korisnik delete(Long id) {
        Korisnik korisnik = findOne(id);
        if(korisnik != null){
            korisnikDAO.delete(id);
        }
        return korisnik;
    }

    @Override
    public List<Korisnik> find(String email, String ime, String prezime, String adresa) {
        return korisnikDAO.find(email, ime, prezime, adresa);
    }


}
