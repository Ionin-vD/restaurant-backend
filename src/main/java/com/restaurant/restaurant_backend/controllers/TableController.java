package com.restaurant.restaurant_backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.restaurant_backend.entity.DiningTable;
import com.restaurant.restaurant_backend.controllers.dopclass.TableBookingRequest;
import com.restaurant.restaurant_backend.entity.Client;
import com.restaurant.restaurant_backend.services.ClientService;
import com.restaurant.restaurant_backend.services.DiningTableService;

@RestController
@RequestMapping("/table")
public class TableController {
    @Autowired
    private DiningTableService tableService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/get_all")
    public List<DiningTable> getAllTable() {
        return tableService.getAllTable();
    }

    @GetMapping("/getNumber/{number}")
    public DiningTable getTableNumber(@PathVariable Integer number) {
        return tableService.findByNumberTable(number).get();
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
