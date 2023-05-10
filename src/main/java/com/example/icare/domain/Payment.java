package com.example.icare.domain;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

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
   private Long payment_id; //primary key
   private Timestamp payment_date;
   private double payment_amount;
   @OneToOne
   @JoinColumn(name = "appointment_id")
   private Appointment appointment;


}