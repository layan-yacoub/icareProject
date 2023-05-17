package com.example.icare.appointment;
import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByNutritionistId(Long nutritionistId);

    //List<Appointment> findByNutritionistIdAndStartTimeBetween(Timestamp from, Timestamp to);

    List<Appointment> findByNutritionistAndStartTimeBetween( Nutritionist nutritionist, LocalDateTime startTime , LocalDateTime endTime);

    List<Appointment> findByNutritionistAndStartTimeGreaterThanEqual(Nutritionist nutritionist, LocalDateTime startTime);

    List<Appointment> findByNutritionistAndEndTimeLessThanEqual( Nutritionist nutritionist, LocalDateTime endTime);

    List<Appointment> findByNutritionist(Nutritionist nutritionist);

    List<Appointment> findAvailableAppointments(List<Nutritionist> availableNutritionists, LocalDate availableDate);

    List<Appointment> findByPatient(Patient patient);
}
