package PetAppBackend.repo;

import PetAppBackend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Pronađi sve termine za određenog ljubimca
    List<Appointment> findAllByPet_Id(Long petId);

    // Pronađi sve termine za određenog vlasnika (preko ljubimaca)
    List<Appointment> findAllByPet_Owner_Id(Long ownerId);
}
