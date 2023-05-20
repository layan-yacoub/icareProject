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
@Table
   public class Payment {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long payment_id; //primary key
   private LocalDate payment_date;
   private double payment_amount;


   @OneToOne
   @JoinColumn
   private Appointment appointment;


}