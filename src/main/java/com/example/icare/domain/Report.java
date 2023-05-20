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
@Table
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long report_id;
    @Column
    private int user_id  ;
    @Column
    private String title;
    @Column
    private String reason;
    @Column
    private String reported;
    @Column
    private String rep_text;
    @Column
    private int admin_id  ;
    @Column
    private boolean visible = true;

    @ManyToOne(fetch = FetchType.LAZY)// Many-to-one relationship with user entity
    private User user;

}

