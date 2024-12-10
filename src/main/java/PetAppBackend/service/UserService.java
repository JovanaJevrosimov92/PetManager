package PetAppBackend.service;


import PetAppBackend.model.DTO.LoginUserDTO;
import PetAppBackend.model.DTO.RegisterUserDTO;
import PetAppBackend.model.Role;
import PetAppBackend.model.User;
import PetAppBackend.repo.RoleRepository;
import PetAppBackend.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;



    // dodajem novog korisnika
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // dohvatam sve korisnike
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // pronalaženje korisnika po id-u
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    // pronalaženje korisnika po imenu korisnika
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // brisanje korisnika po id-u
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    public boolean registerUser(RegisterUserDTO registerUserDTO){
        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())){
            throw new IllegalArgumentException("Passwords do not match!");
        }

        if (userRepository.existsByUsername(registerUserDTO.getUsername())){
            throw new IllegalArgumentException("Username already exists!"); //korisnicko ime vec postoji
        }

        Role userRole=roleRepository.findByName("ROLE_USER")
                .orElseThrow(()-> new IllegalArgumentException("Default role not found in database"));

        User user = modelMapper.map(registerUserDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setRole(userRole);

        userRepository.save(user);
        return true;
    }

    public boolean registerAdmin(RegisterUserDTO registerUserDTO, String adminSecretKey){
        final String SECRET_KEY = "CatDogRabbit123";

        if(!adminSecretKey.equals(SECRET_KEY)){
            throw new SecurityException("Invalid admin secret key.");
        }

        if(userRepository.findByUsername(registerUserDTO.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username is already taken");
        }

        Role adminRole= roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(()-> new IllegalArgumentException("Admin role not found in database"));


        User admin = modelMapper.map(registerUserDTO, User.class);
        admin.setRole(adminRole);
        admin.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

        userRepository.save(admin);

        return true;
    }

    public boolean loginUser(LoginUserDTO loginUserDTO){

        Optional<User> userOptional = userRepository.findByUsername(loginUserDTO.getUsername());

        if(userOptional.isPresent()){
            User user = userOptional.get();
            return passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword());

        }

        return false; //korisnik nije pronadjen
    }



}
