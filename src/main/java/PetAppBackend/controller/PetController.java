package PetAppBackend.controller;

import PetAppBackend.config.UserSession;
import PetAppBackend.model.DTO.AddPetDTO;
import PetAppBackend.model.Pet;
import PetAppBackend.model.PetType;
import PetAppBackend.model.User;
import PetAppBackend.service.PetService;
import PetAppBackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private  PetService petService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSession userSession;


    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("petDTO",new AddPetDTO());
        model.addAttribute("petTypes",petService.getPetTypes());
        return "add-pet";
    }


    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();

        if(pets.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        return petService.getPetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping("/add")
    public ResponseEntity<?> addPet(@Valid @RequestBody AddPetDTO addPetDTO, BindingResult bindingResult) {
        System.out.println("Entering addPEt controller ");
        System.out.println("UserSession name: "+userSession.getUsername());
        System.out.println("UserSession id: "+ userSession.getId());
        if(bindingResult.hasErrors()){
            Map<String,String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
            System.out.println("errors: "+errors);
            return ResponseEntity.badRequest().body(errors);
        }
        if(userSession.getUsername()==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unathorized acess. Please log in.");
        }


        System.out.println("UserSession name: "+userSession.getUsername());
        System.out.println("UserSession id: "+ userSession.getId());


        try{
            Optional<User> userOptional = userService.findUserByUsername(userSession.getUsername());
            System.out.println("User found in database: "+userOptional);
            if(userOptional.isEmpty()){
                return ResponseEntity.status(404).body("Owner not found");
            }
            Pet pet = petService.addPet(addPetDTO);
            System.out.println("Pet added: "+pet);
            return ResponseEntity.ok(pet);
        } catch (IllegalArgumentException e){
            System.out.println("Error: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: "+e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}