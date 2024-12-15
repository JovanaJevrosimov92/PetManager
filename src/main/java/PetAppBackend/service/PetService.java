package PetAppBackend.service;

import PetAppBackend.config.UserSession;
import PetAppBackend.model.DTO.AddPetDTO;
import PetAppBackend.model.Pet;
import PetAppBackend.model.PetType;
import PetAppBackend.model.User;
import PetAppBackend.repo.PetRepository;
import PetAppBackend.repo.PetTypeRepository;
import PetAppBackend.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service

public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSession userSession;


    //za dodavanje novog ljubimca su mi potrebni svi podaci za ljubimca plus koji je tip
    public Pet addPet(AddPetDTO addPetDTO) {
        System.out.println("Entering addPet service method... ");
        System.out.println("DTO type: "+ addPetDTO.getType());

        //proveravam da li u repou za tipove postoji uopste tip koji se unosi (macka, pas itd)
        //i ako nema, bacam exception


        PetType petType = petTypeRepository.findByName(addPetDTO.getType())
                .orElseThrow(()->new IllegalArgumentException("Invalid pet type!"));

        Optional<User> userOptional = userRepository.findByUsername(userSession.getUsername());
        if(userOptional.isEmpty()) {
            throw new IllegalArgumentException("Owner not found!");
        }
        User setOwner = userOptional.get();
        Pet pet = modelMapper.map(addPetDTO, Pet.class);
        pet.setOwner(setOwner);
        pet.setType(petType);

        //cuvam podatke
        return petRepository.save(pet);

    }

    //za dohvatanje svih ljubimaca
    public List<Pet> getAllPets(){

        return petRepository.findAll();
    }


    //dohvatanje ljubimca po id-u

    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }


    //brisanje ljubimca po id-u
    public void deletePet(Long id){
        petRepository.deleteById(id);
    }

    public List<String> getPetTypes() {
        return Arrays.asList("Dog","Cat","Fish","Parrot","Rabbit","Turtle");
    }


}