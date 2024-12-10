package PetAppBackend.service;

import PetAppBackend.model.PetType;
import PetAppBackend.repo.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetTypeService {

   @Autowired
   private PetTypeRepository petTypeRepository;

    // dohvatam sve tipove ljubimaca
    public List<PetType> getAllPetTypes() {
        return petTypeRepository.findAll();
    }

    // za pronalaženje tipa ljubimca po imenu
    public PetType findByName(String name) {
        return petTypeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Pet type not found: " + name));
    }
}
