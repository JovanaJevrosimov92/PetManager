package PetAppBackend.repo;

import PetAppBackend.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    // Pronađi sve ljubimce za određenog vlasnika
    List<Pet> findAllByOwner_Id(Long ownerId);
}
