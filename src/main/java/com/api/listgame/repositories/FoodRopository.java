package com.api.listgame.repositories;

import com.api.listgame.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRopository extends JpaRepository<Food, Long> {
}
