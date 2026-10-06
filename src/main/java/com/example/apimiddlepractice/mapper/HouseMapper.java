package com.example.apimiddlepractice.mapper;

import com.example.apimiddlepractice.dto.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HouseMapper {

    @Select("SELECT * FROM houses ORDER BY expense_date DESC")
    List<House> selectAllHouse();
}
