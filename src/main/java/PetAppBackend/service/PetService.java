package PetAppBackend.service;

import PetAppBackend.model.DTO.AddPetRequest;
import PetAppBackend.model.Pet;
import PetAppBackend.model.PetType;
import PetAppBackend.repo.PetRepository;
import PetAppBackend.repo.PetTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    //za dodavanje novog ljubimca su mi potrebni svi podaci za ljubimca plus koji je tip
    public Pet addPet(AddPetRequest addPetRequest) {

        //proveravam da li u repou za tipove postoji uopste tip koji se unosi (macka, pas itd)
        //i ako nema, bacam exception
        PetType petType = petTypeRepository.findByName(addPetRequest.getTypeName())
                .orElseThrow(()->new IllegalArgumentException("Invalid pet type!"));

        Pet pet = modelMapper.map(addPetRequest, Pet.class);
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

}
