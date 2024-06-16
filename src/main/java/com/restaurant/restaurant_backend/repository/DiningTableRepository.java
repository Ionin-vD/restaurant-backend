package com.restaurant.restaurant_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurant.restaurant_backend.entity.DiningTable;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {
    Optional<DiningTable> findByNumberTable(Integer numberTable);

    @Query("SELECT MAX(d.numberTable) FROM DiningTable d")
    Integer findMaxNumberTable();
}
