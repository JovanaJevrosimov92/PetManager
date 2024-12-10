package PetAppBackend.init;

import PetAppBackend.model.PetType;
import PetAppBackend.repo.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PetTypeLoader implements CommandLineRunner {

    @Autowired
    private PetTypeRepository petTypeRepository;


    @Override
    public void run(String... args) {

        if(petTypeRepository.count()>0) {
            return;
        }
        List<PetType> petTypes = Arrays.asList(
                new PetType(null, "Dog"),
                new PetType(null, "Cat"),
                new PetType(null, "Fish"),
                new PetType(null, "Parrot"),
                new PetType(null, "Rabbit"),
                new PetType(null, "Turtle")
        );
        petTypeRepository.saveAll(petTypes);
        }
    }
