package com.example.icare.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table()
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column()
    private Long message_id ;
    @Column()
    private String messageText ;
    @Column()
    @Lob
    private byte[] attachment;
    @Column()
    private boolean hasAttachment;
    @Column()
    private Timestamp messageDate;

    // Many-to-one relationships

    @ManyToOne
    @JoinColumn()
    private Nutritionist nutritionist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Patient patient;


}
