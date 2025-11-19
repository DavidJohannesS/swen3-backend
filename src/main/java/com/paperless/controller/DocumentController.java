package com.paperless.controller;

import com.paperless.model.Document;
import com.paperless.service.DocumentMapper;
import com.paperless.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@Slf4j
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public Document saveDocument(@RequestBody Document document){
        log.info("POST /api/documents called with document: {}", document);
        return documentService.saveDocument(document);
    }

    @GetMapping("/{id}")
    public Document getById(@PathVariable Long id){
        var doc = documentService.getDocumentById(id);
        log.info("GET /api/documents/{id}", id);
        if (doc == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found with id: " + id);
        }
        return doc;
    }

    @GetMapping("/ping")
    public String pingPong(){
        log.info("GET /api/documents/pingPong called");
        return "Pong";
    }
    @GetMapping
    public List<Document> getAllDocuments(){
        log.info("GET /api/documents called");
        return documentService.getAllDocuments();
    }
}
