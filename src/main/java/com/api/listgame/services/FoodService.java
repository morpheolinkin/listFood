package com.api.listgame.services;

import com.api.listgame.domain.Food;
import com.api.listgame.dto.FoodDto;
import com.api.listgame.repositories.FoodRopository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRopository foodRopository;

    public Food findById(Long id) {
        return foodRopository.findById(id)
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.BAD_REQUEST, "Food not found"));
    }

    public List<Food> ListAll() {
        return foodRopository.findAll();
    }

    public Food convertFromDto(FoodDto foodDto) {
        return new Food(foodDto.id(), foodDto.title(),
                foodDto.image(), foodDto.price());
    }

    public URI buildNewFoodUri(FoodDto foodDto) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(foodDto.id())
                .toUri();
    }

    public void save(Food newFood) {
        newFood.setId(null);
        foodRopository.save(newFood);
    }

    public void delete(Long id) {
        foodRopository.delete(findById(id));
    }

    public void update(Food food) {
        var foodUpdate = findById(food.getId());
        upadateData(foodUpdate, food);
        foodRopository.save(foodUpdate);
    }

    private void upadateData(Food foodUpdate, Food food) {
        foodUpdate.setTitle(food.getTitle());
        foodUpdate.setImage(food.getImage());
        foodUpdate.setPrice(food.getPrice());
    }

    public Page<Food> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return foodRopository.findAll(pageRequest);
    }
}
