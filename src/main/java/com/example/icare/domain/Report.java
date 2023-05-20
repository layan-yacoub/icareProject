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
    @Column(name="user_id")
    private int user_id  ;
    @Column(name="title")
    private String title;
    @Column(name="reason")
    private String reason;
    @Column(name="reported")
    private String reported;
    @Column(name="rep_text")
    private String rep_text;
    @Column(name="admin_id")
    private int admin_id  ;
    @Column(name ="visible")
    private boolean visible = true;

    @ManyToOne(fetch = FetchType.LAZY)// Many-to-one relationship with user entity
    private User user;

}

