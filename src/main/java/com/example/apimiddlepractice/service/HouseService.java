package com.example.apimiddlepractice.service;

import com.example.apimiddlepractice.dto.House;
import com.example.apimiddlepractice.exception.HouseArgumentNotValidException;
import com.example.apimiddlepractice.repository.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {
    private final HouseRepository houseRepository;
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> selectAllHouse() {
        return houseRepository.selectAllHouse();
    }
    public House saveHouse(House house){
        if (house.getAmount() <= 0){
            throw new HouseArgumentNotValidException("Amount is empty");
        }

        if(house.getType() == null || house.getType().isEmpty()){
            throw new HouseArgumentNotValidException("Type is empty");
        }

        if(house.getContent() == null || house.getContent().isEmpty()){
            throw new HouseArgumentNotValidException("Content is empty");
        }

        houseRepository.insertHouse(house);
        return house;
    }
}
