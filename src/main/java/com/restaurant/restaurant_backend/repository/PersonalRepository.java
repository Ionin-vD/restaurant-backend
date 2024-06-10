package com.restaurant.restaurant_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.restaurant_backend.entity.Personal;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
}
