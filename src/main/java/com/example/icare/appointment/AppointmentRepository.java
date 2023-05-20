package com.example.icare.appointment;
import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query("SELECT a FROM Appointment a WHERE a.nutritionist IN :listParam AND a.startTime = :dateParam")
    List<Appointment> findAvailableAppointments(@Param("listParam") List<Appointment> listParam, @Param("dateParam") LocalDateTime dateParam);


    List<Appointment> findByNutritionist(Nutritionist nutritionist);


    List<Appointment> findByPatient(Patient patient);

    @Query("SELECT a FROM Appointment a WHERE a.nutritionist = :nutritionist AND DATE(a.startTime) = :date")
    List<Appointment> findByNutritionistAndDate(@Param("nutritionist") Nutritionist nutritionist, @Param("date") LocalDate date);
}
