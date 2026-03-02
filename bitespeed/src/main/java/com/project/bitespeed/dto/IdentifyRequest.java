package com.project.bitespeed.dto;

import lombok.Data;

@Data
public class IdentifyRequest {
    private String email;
    private String phoneNumber;
}