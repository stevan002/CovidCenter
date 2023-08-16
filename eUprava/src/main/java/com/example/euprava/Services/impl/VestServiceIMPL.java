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
    public void save(Vest vest) {
        if(vestDAO.findOne(vest.getId()) != null){
            vestDAO.update(vest);
        }else{
            vestDAO.save(vest);
        }

    }

    @Override
    public void delete(Long id) {
        vestDAO.delete(id);
    }

    @Override
    public Vest update(Vest vest) {
        vestDAO.update(vest);
        return vest;
    }
}
