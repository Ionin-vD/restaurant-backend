package com.restaurant.restaurant_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.restaurant_backend.controllers.dopclass.LoginRequest;
import com.restaurant.restaurant_backend.entity.Personal;
import com.restaurant.restaurant_backend.services.PersonalService;

@RestController
@RequestMapping("/personal")
public class PersonalController {
    @Autowired
    private PersonalService personalService;

    @GetMapping("/get_all")
    public List<Personal> getAllPersonals() {
        return personalService.getAllPersonals();
    }

    @PostMapping("/check_login")
    public ResponseEntity<Personal> checkLogin(@RequestBody LoginRequest loginRequest) {
        Personal personal = personalService.getPersonalByLoginAndPassword(loginRequest.getLogin(),
                loginRequest.getPassword());
        if (personal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(personal);
    }

    @PostMapping("/add")
    public ResponseEntity<Personal> addPersonal(@RequestBody Personal personal) {
        Personal savedPersonal = personalService.savePersonal(personal);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonal);
    }

    @PutMapping("/update")
    public ResponseEntity<Personal> updatePersonal(@RequestBody Personal personal) {
        Personal updatedPersonal = personalService.updatePersonal(personal);
        if (updatedPersonal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(updatedPersonal);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePersonal(@PathVariable Long id) {
        boolean isRemoved = personalService.deletePersonal(id);
        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}
