package com.example.icare.service;

import com.example.icare.appointment.Appointment;
import com.example.icare.domain.Nutritionist;
import com.example.icare.repository.NutritionistRepository;
import com.example.icare.user.InvalidPasswordException;
import com.example.icare.user.UserNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NutritionistService {

    private final NutritionistRepository nutritionistRepository;


@Autowired
    public NutritionistService(NutritionistRepository nutritionistRepository) {
        this.nutritionistRepository = nutritionistRepository;
    }

    public List<Nutritionist> getAllCenters (){
        return nutritionistRepository.findAll();
    }

    public void deleteNutritionistById(Long nutritionist_id) {
        nutritionistRepository.deleteById(nutritionist_id);
    }

    @SneakyThrows
    public List<Appointment> generateMonthlyAppointments(Long nutritionist_id , int year, Month month, LocalTime startTime, LocalTime endTime, List<DayOfWeek> excludedDays) {

        List<Appointment> generatedAppointments = new ArrayList<>();

        // Validate year and month
        if (year < 0 || month == null) {
            throw new IllegalArgumentException("Invalid year or month");
        }

        // Get the nutritionist entity
        Nutritionist nutritionist = nutritionistRepository.findById(nutritionist_id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Get the number of days in the specified month
        int numDays = YearMonth.of(year, month).lengthOfMonth();

        // Loop through each day of the month
        for (int day = 1; day <= numDays; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            System.out.println(day);
            // Check if the day is excluded
            if (excludedDays.contains(date.getDayOfWeek())) {
                continue;  // Skip the excluded day
            }
            System.out.println(day);
            // Loop through each hour from start time to end time
            LocalDateTime currentDateTime = LocalDateTime.of(date, startTime);
            while (currentDateTime.isBefore(LocalDateTime.of(date, endTime))) {
                Appointment appointment = new Appointment();
                appointment.setStartTime(currentDateTime);
                appointment.setEndTime(currentDateTime.plusHours(1));
                appointment.setNutritionist(nutritionist);

                nutritionist.getAppointments().add(appointment);
                generatedAppointments.add(appointment);

                currentDateTime = currentDateTime.plusHours(1);
                System.out.println("log"+day);
            }
        }
        nutritionistRepository.save(nutritionist); // Save the nutritionist with the generated appointments
        return generatedAppointments;
    }
    // get ALL the generated appointments for a specific nutritionist
    public List<Appointment> getGeneratedAppointments(Long nutritionistId) throws ChangeSetPersister.NotFoundException {
        Optional<Nutritionist> nutritionistOptional = nutritionistRepository.findById(nutritionistId);
        if (nutritionistOptional.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        Nutritionist nutritionist = nutritionistOptional.get();
        return nutritionist.getAppointments();
    }

   // get the generated appointments in specific month for a specific nutritionist
    public List<Appointment> getGeneratedAppointments(Long nutritionistId, YearMonth yearMonth) throws ChangeSetPersister.NotFoundException {
        Optional<Nutritionist> nutritionistOptional = nutritionistRepository.findById(nutritionistId);
        if (nutritionistOptional.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        Nutritionist nutritionist = nutritionistOptional.get();

        List<Appointment> generatedAppointments = nutritionist.getAppointments();

        // Filter appointments based on the specified YearMonth
        return generatedAppointments.stream()
                .filter(appointment -> YearMonth.from(appointment.getStartTime()).equals(yearMonth))
                .collect(Collectors.toList());
    }

    public Optional<Nutritionist> findById(Long nutritionistId) {
        return nutritionistRepository.findById(nutritionistId);
    }



    public Optional<Nutritionist> getNutritionistById(Long nutritionistId)  {
        return nutritionistRepository.findById(nutritionistId);
    }


    public int getRating(Long nutritionistId) {
        int rate;
        rate = nutritionistRepository.getNutritionistById(nutritionistId).getRating();
        return rate;
    }

    public void setAmountOfAppointments(Long id ,double amount) {
        Nutritionist nutritionist = nutritionistRepository.getNutritionistById(id);
        nutritionist.setAmount(amount);
        nutritionistRepository.save(nutritionist);
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

    public void changeEmail(Long nutritionistId, String password, String newEmail) {
            Nutritionist nutritionist = nutritionistRepository.findById(nutritionistId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            if (! (Objects.equals(password, nutritionist.getUser().getPassword()))){
                throw new InvalidPasswordException("Invalid password");
            }

            nutritionist.setEmail(newEmail);
            nutritionistRepository.save(nutritionist);

    }

    public void save(Nutritionist nutritionist) {
        nutritionistRepository.save(nutritionist);
    }
}
