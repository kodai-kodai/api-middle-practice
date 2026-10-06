package com.example.apimiddlepractice.controller;

import com.example.apimiddlepractice.dto.House;
import com.example.apimiddlepractice.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {
    private final HouseService houseService;
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public ResponseEntity<List<House>> selectAllHouse() {
        List<House> houses =  houseService.selectAllHouse();
        return ResponseEntity.ok(houses);
    }

    @PostMapping
    public ResponseEntity<House> saveHouse(@RequestBody House house,
                                         UriComponentsBuilder uriBuilder){
        House createdHouse = houseService.saveHouse(house);
        URI location = uriBuilder.path("/api/houses/{id}").buildAndExpand(createdHouse.getHouseId()).toUri();

        return ResponseEntity.created(location).body(createdHouse);
    }

}
