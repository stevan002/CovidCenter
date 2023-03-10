package com.example.euprava.Services.impl;

import com.example.euprava.Models.VestObolelima;
import com.example.euprava.Services.VestObolelimaService;
import com.example.euprava.dao.VestObolelimaDAO;

import java.util.List;

public class VestObolelimaServiceIMPL implements VestObolelimaService {

    private VestObolelimaDAO vestObolelimaDAO;

    @Override
    public VestObolelima findOne(Long id) {
        return vestObolelimaDAO.findOne(id);
    }

    @Override
    public List<VestObolelima> findAll() {
        return vestObolelimaDAO.findAll();
    }

    @Override
    public VestObolelima save(VestObolelima vestObolelima) {
        vestObolelimaDAO.save(vestObolelima);
        return vestObolelima;
    }

    @Override
    public VestObolelima update(VestObolelima vestObolelima) {
        vestObolelimaDAO.update(vestObolelima);
        return vestObolelima;
    }

    @Override
    public VestObolelima delete(Long id) {
        VestObolelima vestObolelima = vestObolelimaDAO.findOne(id);
        vestObolelimaDAO.delete(id);
        return vestObolelima;
    }
}
