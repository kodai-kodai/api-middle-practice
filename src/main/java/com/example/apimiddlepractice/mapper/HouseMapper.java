package com.example.apimiddlepractice.mapper;

import com.example.apimiddlepractice.dto.House;
import com.example.apimiddlepractice.dto.CategorySummary;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.time.LocalDate;

@Mapper
public interface HouseMapper {

    @Select("SELECT type, category, SUM(amount) AS total_amount FROM house_books " +
            "WHERE trans_date >= #{startDate} AND trans_date < #{endDate} " +
            "GROUP BY type, category ORDER BY type, category")
    List<CategorySummary> selectCategorySummary(@Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

    @Select("SELECT * FROM house_books ORDER BY trans_date DESC")
    List<House> selectAllHouse();

    @Insert("INSERT INTO house_books(amount, type, content, category, trans_date) values (#{amount}, #{type}, #{content}, #{category}, #{transDate})")
    @Options(useGeneratedKeys = true, keyProperty = "houseId,createdAt", keyColumn = "house_id,created_at")
    int insertHouse(House house);

    @Select("SELECT * FROM house_books where house_id = #{houseId}")
    House selectHouseById(long houseId);

    @Delete("DELETE FROM house_books WHERE house_id = #{houseId}")
    int deleteHouseById(long houseId);

    @Update("UPDATE house_books SET amount = #{amount}, type = #{type}, content = #{content}, category = #{category}, trans_date = #{transDate} WHERE house_id = #{houseId}")
    int updateHouse(House house);
}
