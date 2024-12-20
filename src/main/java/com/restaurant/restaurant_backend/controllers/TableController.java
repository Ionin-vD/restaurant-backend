package com.restaurant.restaurant_backend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.restaurant_backend.entity.DiningTable;
import com.restaurant.restaurant_backend.entity.Food;
import com.restaurant.restaurant_backend.controllers.dopclass.TableBookingRequest;
import com.restaurant.restaurant_backend.controllers.dopclass.TableUpdateRequest;
import com.restaurant.restaurant_backend.entity.Client;
import com.restaurant.restaurant_backend.services.ClientService;
import com.restaurant.restaurant_backend.services.DiningTableService;
import com.restaurant.restaurant_backend.services.FoodService;

@RestController
@RequestMapping("/table")
public class TableController {
    @Autowired
    private DiningTableService tableService;

    @Autowired
    private ClientService clientService;
    @Autowired
    private FoodService foodService;

    @GetMapping("/get_all")
    public List<DiningTable> getAllTable() {
        return tableService.getAllTable();
    }

    @GetMapping("/get_number/{number}")
    public DiningTable getTableNumber(@PathVariable Integer number) {
        return tableService.findByNumberTable(number).get();
    }

    @GetMapping("/get_id/{id}")
    public DiningTable getTableId(@PathVariable Integer id) {
        return tableService.findByIdTable(Long.valueOf(id)).get();
    }

    @PutMapping("/book")
    public ResponseEntity<DiningTable> bookTable(@RequestBody TableBookingRequest request) {
        Optional<DiningTable> tableOpt = tableService.findByNumberTable(request.getNumberTable());
        if (tableOpt.isPresent()) {
            DiningTable table = tableOpt.get();

            // Check if client exists or create a new one
            Client client;
            if (request.getClientId() != null) {
                Optional<Client> clientOpt = clientService.getClientById(request.getClientId());
                if (clientOpt.isPresent()) {
                    client = clientOpt.get();
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else {
                client = new Client();
                client.setNameAndSerName(request.getClientName());
                client.setPhoneNumber(request.getClientPhoneNumber());
                client = clientService.saveClient(client);
            }

            table.setClient(client);
            table.setStatus("booked");
            DiningTable updatedTable = tableService.saveTable(table);
            return ResponseEntity.ok(updatedTable);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/cancel_book")
    public ResponseEntity<DiningTable> cancelBookTable(@RequestBody Map<String, Integer> payload) {
        Integer id = payload.get("id");
        Optional<DiningTable> tableOpt = tableService.findByIdTable(Long.valueOf(id));
        if (tableOpt.isPresent()) {
            DiningTable table = tableOpt.get();

            table.setClient(null);
            table.setStatus("available");
            DiningTable updatedTable = tableService.saveTable(table);
            return ResponseEntity.ok(updatedTable);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update_book")
    public ResponseEntity<DiningTable> updateBookTable(@RequestBody TableUpdateRequest request) {
        Long id = request.getId();
        String status = request.getStatus();

        Optional<DiningTable> tableOpt = tableService.findByIdTable(id);
        if (tableOpt.isPresent()) {
            DiningTable table = tableOpt.get();

            if ("available".equals(status)) {
                table.setClient(null);
                table.getFoods().clear();
            }
            table.setStatus(status);
            DiningTable updatedTable = tableService.saveTable(table);
            return ResponseEntity.ok(updatedTable);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/add_food")
    public ResponseEntity<DiningTable> addFoodToTable(
            @RequestParam Long tableId,
            @RequestBody List<Long> foodIds) {

        Optional<DiningTable> tableOpt = tableService.findByIdTable(tableId);

        if (tableOpt.isPresent()) {
            DiningTable table = tableOpt.get();

            // Fetch existing foods or initialize if null
            List<Food> foods = table.getFoods();
            if (foods == null) {
                foods = new ArrayList<>();
            }

            // Add new food items to the list
            for (Long foodId : foodIds) {
                Optional<Food> foodOpt = foodService.findById(foodId);
                if (foodOpt.isPresent()) {
                    foods.add(foodOpt.get());
                } else {
                    // Handle case where foodId doesn't exist
                    return ResponseEntity.notFound().build();
                }
            }

            // Update the dining table with the new list of foods
            table.setFoods(foods);
            DiningTable updatedTable = tableService.saveTable(table);
            return ResponseEntity.ok(updatedTable);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get_ordered_dishes/{tableId}")
    public ResponseEntity<List<Food>> getOrderedDishes(@PathVariable Long tableId) {
        Optional<DiningTable> tableOpt = tableService.findByIdTable(tableId);
        if (tableOpt.isPresent()) {
            List<Food> orderedDishes = tableOpt.get().getFoods();
            return ResponseEntity.ok(orderedDishes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<DiningTable> addTable(@RequestBody DiningTable table) {
        DiningTable addedTable = tableService.addTable(table);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTable);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteLastTable() {
        boolean deleted = tableService.deleteLastTable();
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("cannot be deleted");
        }
    }

}
