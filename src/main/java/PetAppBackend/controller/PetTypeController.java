package PetAppBackend.controller;

import PetAppBackend.model.PetType;
import PetAppBackend.repo.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pet-types")
public class PetTypeController {

    @Autowired
    private PetTypeRepository petTypeRepository;

    @GetMapping
    public ResponseEntity<List<String>> getAllPetTypes() {
        List<String> petTypes = petTypeRepository.findAll()
                .stream()
                .map(PetType::getName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(petTypes);
    }
}
