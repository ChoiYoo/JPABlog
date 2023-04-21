package com.example.jpablog.user.entity;

import com.example.jpablog.user.model.MemberPointType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class MemberPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberPointType memberPointType;

    @Column
    private int point;

}
