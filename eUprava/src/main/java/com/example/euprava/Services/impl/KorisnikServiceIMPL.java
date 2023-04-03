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
    public Korisnik findOneByEmail(String email) {
        return korisnikDAO.findOneByEmail(email);
    }

    @Override
    public Korisnik findOneByEmailAndPassword(String email, String lozinka) {
        return korisnikDAO.findOneByEmailAndPassword(email, lozinka);
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

}
