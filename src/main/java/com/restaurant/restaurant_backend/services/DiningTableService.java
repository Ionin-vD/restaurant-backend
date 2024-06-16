package com.restaurant.restaurant_backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.restaurant_backend.entity.DiningTable;
import com.restaurant.restaurant_backend.repository.DiningTableRepository;

@Service
public class DiningTableService {
    @Autowired
    private DiningTableRepository tableRepository;

    public List<DiningTable> getAllTable() {
        return tableRepository.findAll();
    }

}
