package com.example.euprava.Services;

import com.example.euprava.Models.VestObolelima;

import java.util.List;

public interface VestObolelimaService {
    VestObolelima getOne(Long id);
    List<VestObolelima> findAll();
    void save(VestObolelima vestObolelima);
    void delete(Long id);
}
