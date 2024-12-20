package com.restaurant.restaurant_backend.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public Optional<DiningTable> findByNumberTable(Integer numberTable) {
        return tableRepository.findByNumberTable(numberTable);
    }

    public Optional<DiningTable> findByIdTable(Long id) {
        return tableRepository.findById(id);
    }

    public DiningTable saveTable(DiningTable table) {
        return tableRepository.save(table);
    }

    public DiningTable addTable(DiningTable table) {
        // Получаем максимальный номер стола из базы данных
        Integer maxNumberTable = tableRepository.findMaxNumberTable();
        if (maxNumberTable == null) {
            table.setNumberTable(1);
        } else {
            table.setNumberTable(maxNumberTable + 1);
        }
        // Сохраняем стол в базе данных
        return tableRepository.save(table);
    }

    public void deleteTable(Long tableId) {
        tableRepository.deleteById(tableId);
    }

    public boolean deleteLastTable() {
        List<DiningTable> allTables = tableRepository.findAll();
        if (!allTables.isEmpty()) {
            DiningTable lastTable = allTables.stream()
                    .max(Comparator.comparingInt(DiningTable::getNumberTable))
                    .orElse(null);
            if (lastTable != null && "available".equalsIgnoreCase(lastTable.getStatus())) {
                tableRepository.deleteById(lastTable.getId());
                return true;
            }
        }
        return false;
    }
}
