package PetAppBackend.service;


import PetAppBackend.model.Appointment;
import PetAppBackend.repo.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;


    //dodavanje termina

    public Appointment addAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    //dohvatanje svih termina

    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    //pronalazim termin po id-u

    public Optional<Appointment> findAppointmentById(Long id){
        return appointmentRepository.findById(id);
    }

    //brisem po id-u

    public void deleteAppointmentById(Long id){
        appointmentRepository.deleteById(id);
    }

}
