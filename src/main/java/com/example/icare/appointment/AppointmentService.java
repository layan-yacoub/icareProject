package com.example.icare.appointment;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // to show the available appointments for a specific nutritionist in a specific date
    public List<Appointment> getAvailableAppointments(Nutritionist nutritionist, LocalDate date) {

        LocalDateTime startDateTime = date.atTime(LocalTime.from(nutritionist.getStartDateTime()));
        LocalDateTime endDateTime = date.atTime(LocalTime.from(nutritionist.getEndDateTime()));

        List<Appointment> bookedAppointments = appointmentRepository.findByNutritionistAndStartTimeBetween(nutritionist, startDateTime, endDateTime);
        List<Appointment> availableAppointments = new ArrayList<>();

        LocalDateTime currentDateTime = startDateTime;
        while (currentDateTime.isBefore(endDateTime)) {
            boolean isBooked = false;
            for (Appointment appointment : bookedAppointments) {
                if (currentDateTime.isEqual(appointment.getStartTime())) {
                    isBooked = true;
                    break;
                }
            }
            if (!isBooked) {
                Appointment availableAppointment = new Appointment();
                availableAppointment.setStartTime(currentDateTime);
                availableAppointment.setEndTime(currentDateTime.plusHours(1));
                availableAppointment.setNutritionist(nutritionist);
                availableAppointments.add(availableAppointment);
            }
            currentDateTime = currentDateTime.plusHours(1);
        }

        return availableAppointments;
    }

    //booking appointment method
    public boolean bookAppointment(Nutritionist nutritionist, LocalDateTime startDateTime) {

        // Check if the requested appointment is available
        boolean isAppointmentAvailable = isAppointmentAvailable(nutritionist, startDateTime);

        if (!isAppointmentAvailable) {
            return false;
        }
        Appointment appointment = new Appointment();
        appointment.setStartTime(startDateTime);
        appointment.setEndTime(startDateTime.plusHours(1));
        appointment.setNutritionist(nutritionist);
        appointment.setAmount(nutritionist.getAmount());
        appointment.setAppointmentLink(nutritionist.getLink());
        appointment.setBooked_date(LocalDateTime.now());

        // Perform any necessary validation or business logic for booking the appointment
        boolean paymentSuccess = performPaymentLogic(appointment.getAmount());
        if (paymentSuccess) {
            appointmentRepository.save(appointment);
            return true;
        } else {
            return false;
        }

    }

    //payment logic for the booking in our case we assume that the payment is process is always accepted
    private boolean performPaymentLogic(double appointmentAmount) {
        Payment payment= new Payment();
        payment.setPayment_amount(appointmentAmount);
        payment.setPayment_date(LocalDate.now());


        return true;
    }

    private boolean isAppointmentAvailable(Nutritionist nutritionist, LocalDateTime startDateTime) {
        LocalDateTime endDateTime = startDateTime.plusHours(1);

        List<Appointment> bookedAppointments = appointmentRepository.findByNutritionistAndStartTimeBetween(nutritionist, startDateTime, endDateTime);
        return bookedAppointments.isEmpty();
    }

    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> findByNutritionist(Nutritionist nutritionist) {
        return appointmentRepository.findByNutritionist(nutritionist);
    }

    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient);
    }
    public void save(Appointment appointment) { appointmentRepository.save(appointment);
    }

}






