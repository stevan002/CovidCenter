package com.example.euprava.Services.impl;

import com.example.euprava.Models.Vest;
import com.example.euprava.Services.VestService;
import com.example.euprava.dao.VestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VestServiceIMPL implements VestService {
    @Autowired
    private VestDAO vestDAO;

    @Override
    public Vest findOne(Long id) {
        return vestDAO.findOne(id);
    }

    @Override
    public List<Vest> findAll() {
        return vestDAO.findAll();
    }

    @Override
    public Vest save(Vest vest) {
        vestDAO.save(vest);
        return vest;
    }

    @Override
    public Vest delete(Long id) {
        Vest vest = vestDAO.findOne(id);
        vestDAO.delete(id);
        return vest;
    }

    @Override
    public Vest update(Vest vest) {
        vestDAO.update(vest);
        return vest;
    }
}
