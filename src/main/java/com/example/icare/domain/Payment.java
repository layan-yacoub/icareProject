package com.example.icare.domain;
import com.example.icare.appointment.Appointment;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="payment")
   public class Payment {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name="payment_id")
   private Long payment_id; //primary key
   @Column(name="payment_date")
   private LocalDate payment_date;
   @Column(name="payment_amount")
   private double payment_amount;


   @OneToOne
   @JoinColumn(name = "appointment_id")
   private Appointment appointment;


}