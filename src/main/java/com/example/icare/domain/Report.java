package com.example.icare.domain;

import com.example.icare.user.User;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long report_id;
    private int user_id  ;
    private String title;
    private String reason;
    private String reported;
    private String rep_text;
    private int admin_id  ;


    @ManyToOne(fetch = FetchType.LAZY)// Many-to-one relationship with user entity
    private User user;


}

