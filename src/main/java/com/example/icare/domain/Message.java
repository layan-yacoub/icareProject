package com.example.icare.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long message_id ;
    private String messageText ;

    @Lob
    private byte[] attachment;

    private boolean hasAttachment;

    private Timestamp messageDate;

    // Many-to-one relationships

    @ManyToOne
    private Nutritionist nutritionist;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;


}
