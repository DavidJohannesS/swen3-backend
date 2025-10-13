package com.paperless.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class Document {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String description;

    private Instant createdAt;

}
