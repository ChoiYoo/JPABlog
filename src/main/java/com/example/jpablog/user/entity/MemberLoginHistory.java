package com.example.jpablog.user.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MemberLoginHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long memberId;

    @Column
    private String email;

    @Column
    private String userName;

    @Column
    private LocalDateTime loginDate;

    @Column
    private String ipAddr;

}
