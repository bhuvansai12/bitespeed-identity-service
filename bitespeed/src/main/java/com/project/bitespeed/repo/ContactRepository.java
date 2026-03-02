package com.project.bitespeed.repo;

import com.project.bitespeed.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByEmail(String email);
    List<Contact> findByPhoneNumber(String phoneNumber);
    List<Contact> findByLinkedId(Long linkedId);
}