package com.api.listgame.controllers;

import com.api.listgame.domain.Food;
import com.api.listgame.dto.FoodDto;
import com.api.listgame.services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Food> list(@PathVariable Long id){
        return ResponseEntity.ok(foodService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<FoodDto>> listAllFoods(){
        var foodDtoList = foodService.ListAll().stream().map(FoodDto::new).toList();
        return ResponseEntity.ok().body(foodDtoList);
    }

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody FoodDto foodDto){
        Food newFood = foodService.convertFromDto(foodDto);
        foodService.save(newFood);
        return ResponseEntity.created(foodService.buildNewFoodUri(foodDto)).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        foodService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody FoodDto obj, @PathVariable Long id){
        Food food = foodService.convertFromDto(obj);
        food.setId(id);
        foodService.update(food);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/page")
    public ResponseEntity<Page<FoodDto>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "title") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Food> list = foodService.findPage(page, linesPerPage, orderBy, direction);
        Page<FoodDto> listDto = list.map(FoodDto::new);
        return ResponseEntity.ok().body(listDto);
    }

}