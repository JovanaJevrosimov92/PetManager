package PetAppBackend.repo;

import PetAppBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Pronađi korisnika po korisničkom imenu (za login)
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
