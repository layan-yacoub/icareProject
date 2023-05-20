package com.example.icare.service;

import com.example.icare.appointment.Appointment;
import com.example.icare.appointment.AppointmentRepository;
import com.example.icare.domain.Nutritionist;
import com.example.icare.repository.NutritionistRepository;
import com.example.icare.user.InvalidPasswordException;
import com.example.icare.user.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NutritionistService {

    private final NutritionistRepository nutritionistRepository;
    private final AppointmentRepository appointmentRepository;


@Autowired
    public NutritionistService(NutritionistRepository nutritionistRepository, AppointmentRepository appointmentRepository) {
        this.nutritionistRepository = nutritionistRepository;
    this.appointmentRepository = appointmentRepository;
}

    public void setStartAndEndTime( Long nutritionist_id, LocalTime start , LocalTime end){
        nutritionistRepository.getNutritionistById(nutritionist_id).setStartTime(start);
        nutritionistRepository.getNutritionistById(nutritionist_id).setEndTime(end);
    }
    @Transactional
    public void addOffDay(Long nutritionistId, LocalDate offDay) throws ChangeSetPersister.NotFoundException {
        Nutritionist nutritionist = nutritionistRepository.findById(nutritionistId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<LocalDate> offDays = nutritionist.getOffDays();
        offDays.add(offDay);
        nutritionistRepository.save(nutritionist);
    }

    public void removeOffDay(Long nutritionistId, LocalDate offDay) {
        Nutritionist nutritionist = nutritionistRepository.findByIdWithOffDays(nutritionistId)
                .orElseThrow(() -> new RuntimeException("Nutritionist not found"));

        nutritionist.getOffDays().remove(offDay);

        nutritionistRepository.save(nutritionist);
    }
    public List<Appointment> getBookedAppointments(Long nutritionistId){
    return nutritionistRepository.getNutritionistById(nutritionistId).getBookedAppointments();
    }

    public int getRating(Long nutritionistId) {
        int rate;
        rate = nutritionistRepository.getNutritionistById(nutritionistId).getRating();
        return rate;
    }

    public void setAmountOfAppointments(Long id ,double amount) {
        nutritionistRepository.getNutritionistById(id).setAmount(amount);
    }

    public void changeEmail(Long nutritionistId, String password, String newEmail) {
        Nutritionist nutritionist = nutritionistRepository.findById(nutritionistId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (! (Objects.equals(password, nutritionist.getUser().getPassword()))){
            throw new InvalidPasswordException("Invalid password");
        }

        nutritionist.setEmail(newEmail);
        nutritionistRepository.save(nutritionist);

    }


    //Admin-Patient
    public List<Nutritionist> getAllCenters (){
        return nutritionistRepository.findAll();
    }

    public void deleteNutritionistById(Long nutritionist_id) {
        nutritionistRepository.deleteById(nutritionist_id);
    }

    public void saveNutritionist(Nutritionist nutritionist) {
        nutritionistRepository.save(nutritionist);
    }

    public List<Nutritionist> getAllCentersWithStatus(boolean status) {
        return nutritionistRepository.findByStatus(status);
    }

    public Nutritionist getNutritionistByEmail(String email) {
        return nutritionistRepository.findByEmail(email);
    }


    public Optional<Nutritionist> findById(Long nutritionistId) {
        return nutritionistRepository.findById(nutritionistId);
    }

    public Optional<Nutritionist> getNutritionistById(Long nutritionistId)  {
        return nutritionistRepository.findById(nutritionistId);
    }







}
