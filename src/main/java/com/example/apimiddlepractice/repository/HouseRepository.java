package com.example.apimiddlepractice.repository;

import com.example.apimiddlepractice.mapper.HouseMapper;
import org.springframework.stereotype.Repository;

@Repository
public class HouseRepository {
    private final HouseMapper houseMapper;

    public HouseRepository(HouseMapper houseMapper) {
        this.houseMapper = houseMapper;
    }


}
