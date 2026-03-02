package com.project.bitespeed.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String phoneNumber;

    private Long linkedId;
    private String linkPrecedence;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}