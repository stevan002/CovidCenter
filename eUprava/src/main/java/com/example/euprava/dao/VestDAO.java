package com.example.euprava.dao;

import com.example.euprava.Models.Vest;

import java.util.List;

public interface VestDAO {
    public Vest findOne(Long id);
    public List<Vest> findAll();
    public int save(Vest vest);
    public int delete(Long id);
    public int update(Vest vest);
}
