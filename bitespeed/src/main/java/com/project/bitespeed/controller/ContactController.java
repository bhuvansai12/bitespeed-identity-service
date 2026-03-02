package com.project.bitespeed.controller;

import com.project.bitespeed.dto.IdentifyRequest;
import com.project.bitespeed.dto.IdentifyResponse;
import com.project.bitespeed.server.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/identify")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService service;

    @PostMapping
    public IdentifyResponse identify(@RequestBody IdentifyRequest request) {
        return service.identify(request);
    }
}