package com.project.bitespeed.server;

import com.project.bitespeed.dto.IdentifyRequest;
import com.project.bitespeed.dto.IdentifyResponse;
import com.project.bitespeed.entity.Contact;
import com.project.bitespeed.repo.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.LinkedHashSet;
import java.util.Comparator;
@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository repo;

    public IdentifyResponse identify(IdentifyRequest req) {

        List<Contact> matches = new ArrayList<>();

        if (req.getEmail() != null)
            matches.addAll(repo.findByEmail(req.getEmail()));

        if (req.getPhoneNumber() != null)
            matches.addAll(repo.findByPhoneNumber(req.getPhoneNumber()));

        // ✅ no match → create primary
        if (matches.isEmpty()) {
            Contact primary = createPrimary(req);
            return buildResponse(primary);
        }

        // ✅ find oldest as primary
        Contact primary = matches.stream()
                .min(Comparator.comparing(Contact::getCreatedAt))
                .orElseThrow();

        // ✅ merge multiple primaries
        for (Contact c : matches) {
            if (!c.getId().equals(primary.getId())
                    && "primary".equals(c.getLinkPrecedence())) {

                c.setLinkPrecedence("secondary");
                c.setLinkedId(primary.getId());
                repo.save(c);
            }
        }

        // ✅ check if new info → create secondary
        boolean emailExists = matches.stream()
                .anyMatch(c -> Objects.equals(c.getEmail(), req.getEmail()));

        boolean phoneExists = matches.stream()
                .anyMatch(c -> Objects.equals(c.getPhoneNumber(), req.getPhoneNumber()));

        if (!emailExists || !phoneExists) {
            Contact secondary = Contact.builder()
                    .email(req.getEmail())
                    .phoneNumber(req.getPhoneNumber())
                    .linkedId(primary.getId())
                    .linkPrecedence("secondary")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            repo.save(secondary);
        }

        return buildResponse(primary);
    }

    private Contact createPrimary(IdentifyRequest req) {
        Contact c = Contact.builder()
                .email(req.getEmail())
                .phoneNumber(req.getPhoneNumber())
                .linkPrecedence("primary")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return repo.save(c);
    }

    private IdentifyResponse buildResponse(Contact primary) {

        List<Contact> linked = repo.findByLinkedId(primary.getId());

        List<Contact> all = new ArrayList<>();
        all.add(primary);
        all.addAll(linked);

        Set<String> emails = all.stream()
                .map(Contact::getEmail)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> phones = all.stream()
                .map(Contact::getPhoneNumber)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        List<Long> secondaryIds = linked.stream()
                .map(Contact::getId)
                .toList();

        return IdentifyResponse.builder()
                .contact(
                        IdentifyResponse.ContactData.builder()
                                .primaryContatctId(primary.getId())
                                .emails(new ArrayList<>(emails))
                                .phoneNumbers(new ArrayList<>(phones))
                                .secondaryContactIds(secondaryIds)
                                .build()
                ).build();
    }
}