package PetAppBackend.controller;

import PetAppBackend.config.UserSession;
import PetAppBackend.model.DTO.LoginUserDTO;
import PetAppBackend.model.DTO.RegisterUserDTO;
import PetAppBackend.model.Role;
import PetAppBackend.model.User;
import PetAppBackend.repo.RoleRepository;

import PetAppBackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSession userSession;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        if (!model.containsAttribute("loginUserDTO")) {
            model.addAttribute("loginUserDTO", new LoginUserDTO());
        }
        return "login";
    }
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        if (!model.containsAttribute("registerUserDTO")) {
            model.addAttribute("registerUserDTO", new RegisterUserDTO());
        }
        return "login";
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserDTO loginUserDTO, BindingResult bindingResult) {

        // Validacija ulaznih podataka
        if (bindingResult.hasErrors()) {
            Map<String,String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error-> errors.put(error.getField(),error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        // Provera da li korisnik postoji
        Optional<User> user = userService.findUserByUsername(loginUserDTO.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        // Provera lozinke
        if (!passwordEncoder.matches(loginUserDTO.getPassword(), user.get().getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        userSession.login(user.get());
        System.out.println("username: "+ user.get().getUsername());

        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDTO registerUserDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String,String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error-> errors.put(error.getField(),error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        if(!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())){


            return ResponseEntity.badRequest().body("Passwords do not match");

        }


        if(userService.registerUser(registerUserDTO)){
            return ResponseEntity.ok("User registered successfully");
        }


        return ResponseEntity.badRequest().body("User already exists");
    }

    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterUserDTO registerUserDTO,
                                                @RequestParam("secretKey") String secretKey) {

        userService.registerAdmin(registerUserDTO, secretKey);
        return ResponseEntity.ok("Admin registered successfully!");
    }
}
