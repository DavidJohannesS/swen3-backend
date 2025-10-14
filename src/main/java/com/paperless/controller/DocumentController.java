package com.paperless.controller;

import com.paperless.model.Document;
import com.paperless.service.DocumentMapper;
import com.paperless.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public void createDocument(@RequestBody Document document) throws SQLException {
        log.info("POST /api/documents called with document: {}", document);
        documentService.create(document);
    }

    @GetMapping("/{id}")
    public Document getById(@PathVariable Long id) throws SQLException {
        var doc = documentService.getDocumentById(id);
        log.info("GET /api/documents/{id}", id);
        if (doc == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found with id: " + id);
        }
        return doc;
    }


}
