package com.restaurant.restaurant_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.restaurant_backend.entity.Personal;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
    Personal findByLoginAndPassword(String login, String password);

    List<Personal> findAllByLoginNot(String login);
}
