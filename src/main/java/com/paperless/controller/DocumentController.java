package com.paperless.controller;

import com.paperless.model.Document;
import com.paperless.service.DocumentMapper;
import com.paperless.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public Document getById(@PathVariable UUID id) throws SQLException {
        log.info("GET /api/documents/{id}", id);
        return documentService.getDocumentById(id);
    }


}
