package com.restaurant.restaurant_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.restaurant_backend.entity.DiningTable;
import com.restaurant.restaurant_backend.services.DiningTableService;

@RestController
@RequestMapping("/table")
public class TableController {
    @Autowired
    private DiningTableService tableService;

    @GetMapping("/get_all")
    public List<DiningTable> getAllTable() {
        return tableService.getAllTable();
    }
}
