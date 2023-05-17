package com.example.icare.service;

import com.example.icare.appointment.Appointment;
import com.example.icare.appointment.Availability;
import com.example.icare.appointment.AvailabilityRequest;
import com.example.icare.domain.Nutritionist;
import com.example.icare.repository.NutritionistRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Optional;

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
    public void generateMonthlyAppointments(Long nutritionist_id , int year, Month month, LocalTime startTime, LocalTime endTime, List<DayOfWeek> excludedDays) {


        // Get the number of days in the specified month
        int numDays = YearMonth.of(year, month).lengthOfMonth();

        // Loop through each day of the month
        for (int day = 1; day <= numDays; day++) {
            LocalDate date = LocalDate.of(year, month, day);

            // Check if the day is excluded
            if (excludedDays.contains(date.getDayOfWeek())) {
                continue;  // Skip the excluded day
            }

            // Loop through each hour from start time to end time
            LocalDateTime currentDateTime = LocalDateTime.of(date, startTime);
            while (currentDateTime.isBefore(LocalDateTime.of(date, endTime))) {
                Appointment appointment = new Appointment();
                appointment.setStartTime(currentDateTime);
                appointment.setEndTime(currentDateTime.plusHours(1));
                appointment.setNutritionist(nutritionistRepository.getReferenceById(nutritionist_id));

                getNutritionistById(nutritionist_id).addAppointments(appointment);

                currentDateTime = currentDateTime.plusHours(1);
            }
        }

    }

    public Optional<Nutritionist> findById(Long nutritionistId) {
        return nutritionistRepository.findById(nutritionistId);
    }

    public void addAvailability(Long nutritionistId, AvailabilityRequest availabilityRequest) {
        Nutritionist nutritionist;
        try {
            nutritionist = getNutritionistById(nutritionistId);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException("Nutritionist Not Found");
        }
        Availability availability = createAvailabilityFromRequest(availabilityRequest);
        nutritionist.getAvailabilities().add(availability);
        nutritionistRepository.save(nutritionist);
    }

    public void removeAvailability(Long nutritionistId, AvailabilityRequest availabilityRequest) {
        Nutritionist nutritionist ;
        try {
            nutritionist = getNutritionistById(nutritionistId);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException("Nutritionist Not Found");
        }
        Availability availability = createAvailabilityFromRequest(availabilityRequest);
        nutritionist.getAvailabilities().remove(availability);
        nutritionistRepository.save(nutritionist);
    }

    private Nutritionist getNutritionistById(Long nutritionistId) throws ChangeSetPersister.NotFoundException {
        return nutritionistRepository.findById(nutritionistId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    private Availability createAvailabilityFromRequest(AvailabilityRequest availabilityRequest) {
        LocalDate availableDate = availabilityRequest.getAvailableDate();
        LocalTime startTime = availabilityRequest.getStartTime();
        LocalTime endTime = availabilityRequest.getEndTime();

        return new Availability(availableDate, startTime, endTime);
    }

    public int getRating(Long nutritionistId) {
        int rate;
        try {
            rate = getNutritionistById(nutritionistId).getRating();
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException("Nutritionist has not been rated before");
        }
        return rate;
    }

    public void setAmountOfAppointments(Long id ,double amount) throws ChangeSetPersister.NotFoundException {
        Nutritionist nutritionist = getNutritionistById(id);
        nutritionist.setAmount(amount);
        nutritionistRepository.save(nutritionist);
    }
    public void saveNutritionist(Nutritionist nutritionist) {
        nutritionistRepository.save(nutritionist);
    }

}
