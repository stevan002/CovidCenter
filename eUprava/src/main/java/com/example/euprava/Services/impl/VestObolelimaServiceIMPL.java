package com.example.euprava.Services.impl;

import com.example.euprava.Models.VestObolelima;
import com.example.euprava.Services.VestObolelimaService;
import com.example.euprava.dao.VestObolelimaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VestObolelimaServiceIMPL implements VestObolelimaService {
    @Autowired
    private VestObolelimaDAO vestObolelimaDAO;

    @Override
    public VestObolelima getOne(Long id) {
        return vestObolelimaDAO.findOne(id);
    }

    @Override
    public List<VestObolelima> findAll() {
        return vestObolelimaDAO.findAll();
    }

    @Override
    public void save(VestObolelima vestObolelima) {
        if(vestObolelimaDAO.findOne(vestObolelima.getId()) != null){
            vestObolelimaDAO.update(vestObolelima);
        }else{
            vestObolelimaDAO.save(vestObolelima);
        }
    }

    @Override
    public void delete(Long id) {
        vestObolelimaDAO.delete(id);
    }
}
