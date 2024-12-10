package PetAppBackend.repo;

import PetAppBackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Pronađi ulogu po imenu
    Optional<Role> findByName(String name);
}
