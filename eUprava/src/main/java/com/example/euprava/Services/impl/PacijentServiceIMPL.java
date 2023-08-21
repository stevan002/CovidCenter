package com.example.euprava.Services.impl;

import com.example.euprava.Models.Pacijent;
import com.example.euprava.Services.PacijentService;
import com.example.euprava.dao.PacijentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacijentServiceIMPL implements PacijentService {

    @Autowired
    private PacijentDAO pacijentDAO;

    @Override
    public Pacijent findOne(Long id) {
        return pacijentDAO.findOne(id);
    }

    @Override
    public List<Pacijent> listAll() {
        return pacijentDAO.listAll();
    }

    @Override
    public void save(Pacijent pacijent) {
        pacijentDAO.save(pacijent);
    }

    @Override
    public void update(Pacijent pacijent) {
        pacijentDAO.update(pacijent);
    }

    @Override
    public void delete(Long id) {
        pacijentDAO.delete(id);
    }
}
