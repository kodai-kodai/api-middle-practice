package com.example.apimiddlepractice.service;

import com.example.apimiddlepractice.dto.House;
import com.example.apimiddlepractice.exception.HouseArgumentNotValidException;
import com.example.apimiddlepractice.exception.HouseNotFoundException;
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

    public House selectHouseById(long houseId){
        House house =  houseRepository.selectHouseById(houseId);
        if(house == null){
            throw new HouseNotFoundException(houseId);
        }
        return house;
    }

    public boolean deleteHouseById(long houseId) {
        if (!houseRepository.deleteHouseById(houseId)){
            throw new HouseNotFoundException(houseId);
        }
        return true;
    }

    public House updateHouse(House house, long id){
        // title が空の場合のエラー
        // title が空の場合のバリデーション
        if (house.getAmount() == 0){
            throw new HouseArgumentNotValidException("Amount is empty");
        }

        // content が空の場合のバリデーション
        if(house.getContent() == null || house.getContent().isEmpty()){
            throw new HouseArgumentNotValidException("Content is empty");
        }
        if(house.getType() == null || house.getType().isEmpty()){
            throw new HouseArgumentNotValidException("Type is empty");
        }
        if(house.getCategory() == null || house.getCategory().isEmpty()){
            throw new HouseArgumentNotValidException("Category is empty");
        }
        

        // 指定されたIDが存在しない場合のバリデーション
        House targetHouse = houseRepository.selectHouseById(id);
        if (targetHouse == null){
            throw new HouseNotFoundException(id);
        }
        house.setHouseId(id);
        house.setCreatedAt(targetHouse.getCreatedAt());
        return houseRepository.updateHouse(house);
    }
}
