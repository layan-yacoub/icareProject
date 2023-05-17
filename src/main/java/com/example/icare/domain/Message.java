package com.example.icare.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "message_id")
    private Long message_id ;
    @Column(name = "message_text")
    private String messageText ;
    @Column(name = "attachment")
    @Lob
    private byte[] attachment;
    @Column(name = "has_attachment")
    private boolean hasAttachment;
    @Column(name = "message_date")
    private Timestamp messageDate;

    @ManyToOne
    @JoinColumn(name = "nutritionist_id")
    private Nutritionist nutritionist;

    @ManyToOne(fetch = FetchType.LAZY) // Many-to-one relationship with nutritionist
    @JoinColumn(name = "patient_id")
    private Patient patient;


}
