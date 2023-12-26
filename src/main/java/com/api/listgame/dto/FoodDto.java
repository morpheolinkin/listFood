package com.api.listgame.dto;

import com.api.listgame.domain.Food;

import java.io.Serializable;

/**
 * DTO for {@link com.api.listgame.domain.Food}
 */
public record FoodDto
        (Long id, String title, String image, Integer price)
        implements Serializable {

    public FoodDto (Food obj){
        this(obj.getId(), obj.getTitle(), obj.getImage(), obj.getPrice());
    }
}