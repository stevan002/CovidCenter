package com.example.euprava.Services;

import com.example.euprava.Models.VestObolelima;

import java.util.List;

public interface VestObolelimaService {
    VestObolelima findOne(Long id);
    List<VestObolelima> findAll();
    VestObolelima save(VestObolelima vestObolelima);
    VestObolelima update(VestObolelima vestObolelima);
    VestObolelima delete(Long id);
}
