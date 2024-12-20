package com.restaurant.restaurant_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.restaurant_backend.entity.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
