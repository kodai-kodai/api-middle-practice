package com.example.apimiddlepractice.service;

import com.example.apimiddlepractice.dto.House;
import com.example.apimiddlepractice.dto.CategorySummary;
import com.example.apimiddlepractice.dto.MonthlySummary;
import com.example.apimiddlepractice.exception.HouseArgumentNotValidException;
import com.example.apimiddlepractice.exception.HouseNotFoundException;
import com.example.apimiddlepractice.repository.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

@Service
public class HouseService {
    private final HouseRepository houseRepository;
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public MonthlySummary selectMonthlySummary(String month) {
        List<CategorySummary> categories = selectCategorySummary(month);
        long income = categories.stream()
                .filter(category -> "収入".equals(category.getType()))
                .mapToLong(CategorySummary::getTotalAmount).sum();
        long expense = categories.stream()
                .filter(category -> "支出".equals(category.getType()))
                .mapToLong(CategorySummary::getTotalAmount).sum();
        return new MonthlySummary(month, income, expense, income - expense);
    }

    public List<CategorySummary> selectCategorySummary(String month) {
        YearMonth targetMonth = parseMonth(month);
        return houseRepository.selectCategorySummary(
                targetMonth.atDay(1), targetMonth.plusMonths(1).atDay(1));
    }

    private YearMonth parseMonth(String month) {
        if (month == null || !month.matches("[0-9]{4}-(0[1-9]|1[0-2])")
                || month.startsWith("0000")) {
            throw new HouseArgumentNotValidException("month must be in yyyy-MM format (year: 0001-9999)");
        }
        try {
            return YearMonth.parse(month);
        } catch (DateTimeParseException e) {
            throw new HouseArgumentNotValidException("month must be a valid month in yyyy-MM format");
        }
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
