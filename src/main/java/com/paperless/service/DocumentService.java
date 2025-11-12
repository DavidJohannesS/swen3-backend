package com.paperless.service;

import com.paperless.model.Document;
import com.paperless.model.DocumentStatus;
import com.paperless.repository.DocumentEntity;
import com.paperless.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public Document saveDocument(Document document) {
        try {
            log.info("Create document request: {}", document.getTitle());

            document.setUploadDate(Instant.now());
            document.setStatus(DocumentStatus.UPLOADED);

            var entity = documentMapper.toEntity(document);
            var savedEntity = documentRepository.save(entity);
            return documentMapper.toDto(savedEntity);
        } catch (Exception e) {
            log.error("Error saving document: {}", e.getMessage());
            throw new RuntimeException("Failed to save document", e);
        }
    }

    public Document getDocumentById(Long id) {
        try {
            log.info("Get document by ID request: {}", id);
            return documentRepository.findById(id)
                    .map(entity -> {
                        try {
                            return documentMapper.toDto(entity);
                        } catch (Exception e) {
                            log.error("Error mapping document entity to DTO: {}", e.getMessage());
                            throw new RuntimeException("Mapping error", e);
                        }
                    })
                    .orElse(null);
        } catch (Exception e) {
            log.error("Error retrieving document: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve document", e);
        }
    }

    public List<Document> getAllDocuments(){
        try{
            log.info("Get all documents request");
            return documentRepository.findAll()
                    .stream()
                    .map(entity -> {)
                        try {
                            return documentMapper.toDto(entity);
                        } catch (Exception e) {
                            log.error("Error mapping document entity to DTO: {}", e.getMessage());
                            throw new RuntimeException("Mapping error", e);
                        }
                    })
                    .toList();
        }catch (Exception e){
            log.error("Error retrieving documents: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve documents", e);
        }
    }




}
