package com.example.euprava.Services;

import com.example.euprava.Models.Vest;

import java.util.List;

public interface VestService {
    Vest findOne(Long id);
    List<Vest> findAll();
    Vest save(Vest vest);
    Vest delete(Long id);
    Vest update(Vest vest);
}
