package PetAppBackend.repo;

import PetAppBackend.model.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType,Long> {


    Optional<PetType> findByName(String name);
}
