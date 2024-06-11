package com.restaurant.restaurant_backend.services;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.restaurant_backend.entity.Food;
import com.restaurant.restaurant_backend.repository.FoodRepository;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    public Food saveFood(Food food) {
        return foodRepository.save(food);
    }

    public Food updateFood(Food food) {
        Optional<Food> existingFood = foodRepository.findById(food.getId());
        if (existingFood.isPresent()) {
            return foodRepository.save(food);
        }
        return null;
    }

    public boolean deleteFood(Long id) {
        if (foodRepository.existsById(id)) {
            foodRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
