package com.example.apimiddlepractice.repository;

import com.example.apimiddlepractice.dto.House;
import com.example.apimiddlepractice.mapper.HouseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HouseRepository {
    private final HouseMapper houseMapper;

    public HouseRepository(HouseMapper houseMapper) {
        this.houseMapper = houseMapper;
    }

    public List<House> selectAllHouse() {
        return houseMapper.selectAllHouse();
    }

    public int insertHouse(House house) {
        return houseMapper.insertHouse(house);
    }


}
