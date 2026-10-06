package com.example.apimiddlepractice.mapper;

import com.example.apimiddlepractice.dto.House;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HouseMapper {

    @Select("SELECT * FROM house_books ORDER BY trans_date DESC")
    List<House> selectAllHouse();

    @Insert("INSERT INTO house_books(amount, type, content, category, trans_date) values (#{amount}, #{type}, #{content}, #{category}, #{transDate})")
    @Options(useGeneratedKeys = true, keyProperty = "houseId,createdAt", keyColumn = "house_id,created_at")
    int insertHouse(House house);
}
