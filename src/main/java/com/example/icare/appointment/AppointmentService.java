package com.example.icare.appointment;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.domain.Payment;
import com.example.icare.repository.NutritionistRepository;
import com.example.icare.repository.PatientRepository;
import com.example.icare.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final NutritionistRepository nutritionistRepository;
    private final PaymentRepository paymentRepository;

    private final PatientRepository patientRepository;


    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, NutritionistRepository nutritionistRepository, PaymentRepository paymentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.nutritionistRepository = nutritionistRepository;
        this.paymentRepository = paymentRepository;
        this.patientRepository = patientRepository;
    }


    // get Available appointments in specific date for a specific nutritionist
    public List<Appointment> getAvailableAppointments(Long nutritionistId, LocalDate date) throws Exception  {
        // Get the nutritionist by ID
        Nutritionist nutritionist = nutritionistRepository.findById(nutritionistId)
                .orElseThrow(() -> new EntityNotFoundException("Nutritionist not found"));

        // Get the nutritionist by ID along with the offDays collection
        Nutritionist nutritionist1 = nutritionistRepository.fetchOffDays(nutritionistId);

        // Check if the date is an off day for the nutritionist
        if (nutritionist1.getOffDays().contains(date)) {
            throw new Exception("The selected date is an off day for the nutritionist");
        }

        // Get the booked appointments for the date
        List<Appointment> bookedAppointments = appointmentRepository.findByNutritionistAndDate(nutritionist, date);

        // Get the start and end time of the nutritionist
        LocalTime startTime = nutritionist.getStartTime();
        LocalTime endTime = nutritionist.getEndTime();

        // Create a list to hold available appointments
        List<Appointment> availableAppointments = new ArrayList<>();

        // Iterate over the time slots from start to end time
        while (startTime.isBefore(endTime)) {
            // Check if the current time slot is booked
            boolean isBooked = false;
            for (Appointment appointment : bookedAppointments) {
                LocalTime bookedTime = appointment.getStartTime().toLocalTime();
                if (startTime.equals(bookedTime)) {
                    isBooked = true;
                    break;
                }
            }

            // If the time slot is not booked, add it to the available appointments list
            if (!isBooked) {
                Appointment availableAppointment = new Appointment();
                availableAppointment.setStartTime(LocalDateTime.of(date, startTime));
                availableAppointments.add(availableAppointment);
            }

            // Increment the start time by 1 hour
            startTime = startTime.plusHours(1);
        }

        return availableAppointments;}

    //booking appointment method
    public boolean bookAppointment( Patient patient,Nutritionist nutritionist, LocalDateTime startDateTime) {

        Appointment appointment = new Appointment();
        appointment.setStartTime(startDateTime);
        appointment.setEndTime(startDateTime.plusHours(1));
        appointment.setNutritionist(nutritionist);
        appointment.setAmount(nutritionist.getAmount());
        appointment.setAppointmentLink(nutritionist.getLink());
        appointment.setBooked_date(LocalDateTime.now());
        appointment.setPatient( patient);


       // Explicitly initialize the collection
        nutritionist.getBookedAppointments().add(appointment);
        // Perform any necessary validation or business logic for booking the appointment
        boolean paymentSuccess = performPaymentLogic(appointment,appointment.getAmount());
        if (paymentSuccess) {
            appointmentRepository.save(appointment);
            nutritionist.getBookedAppointments().add(appointment);
            nutritionistRepository.save(nutritionist);
            patientRepository.save(patient);

            return true;
        } else {
            return false;
        }

    }

    //payment logic for the booking in our case we assume that the payment is process is always accepted
    private boolean performPaymentLogic(Appointment appointment ,double appointmentAmount) {
        Payment payment= new Payment();
        payment.setPayment_amount(appointmentAmount);
        payment.setPayment_date(LocalDate.now());
        payment.setAppointment(appointment);
       // paymentRepository.save(payment);

        return true;
    }

    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }

    //get the appointments for specific user
    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient);
    }

    public void save(Appointment appointment) { appointmentRepository.save(appointment);
    }

}






