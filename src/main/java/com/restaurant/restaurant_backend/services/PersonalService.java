package com.restaurant.restaurant_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.restaurant_backend.entity.Personal;
import com.restaurant.restaurant_backend.repository.PersonalRepository;

import jakarta.annotation.PostConstruct;

@Service
public class PersonalService {
    @Autowired
    private PersonalRepository personalRepository;

    public List<Personal> getAllPersonals() {
        return personalRepository.findAllByLoginNot("SUPER_USER");
    }

    @PostConstruct
    public void init() {
        if (personalRepository.count() == 0) {
            Personal personal = new Personal();
            personal.setNameAndSerName("SUPER_USER");
            personal.setLogin("SUPER_USER");
            personal.setPassword("SUPER_USER");
            personal.setRole("admin");
            personalRepository.save(personal);
        }
    }

    public Personal getPersonalByLoginAndPassword(String login, String password) {
        return personalRepository.findByLoginAndPassword(login, password);
    }

    public Personal savePersonal(Personal personal) {
        return personalRepository.save(personal);
    }

    public Personal updatePersonal(Personal personal) {
        Optional<Personal> existingPersonal = personalRepository.findById(personal.getId());
        if (existingPersonal.isPresent()) {
            return personalRepository.save(personal);
        }
        return null;
    }

    public boolean deletePersonal(Long id) {
        if (personalRepository.existsById(id)) {
            personalRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
