package com.example.euprava.dao;

import com.example.euprava.Models.Vest;
import com.example.euprava.Models.VestObolelima;

import java.util.List;

public interface VestObolelimaDAO {
    public VestObolelima findOne(Long id);
    public List<VestObolelima> findAll();
    public int save(VestObolelima vestObolelima);
    public int update(VestObolelima vestObolelima);
    public int delete(Long id);
}
