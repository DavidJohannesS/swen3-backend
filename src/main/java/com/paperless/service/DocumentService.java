package com.paperless.service;

import com.paperless.model.Document;
import com.paperless.repository.DocumentEntity;
import com.paperless.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public void create(Document document) throws SQLException {
        log.info("Create document request: {}", document.getTitle());
        var entity = documentMapper.toEntity(document);
        documentRepository.save(entity);
    }

    public Document getDocumentById(Long id) throws SQLException {
        log.info("Get document by ID request: {}", id);
        var entity = documentRepository.findById(id)
                .orElseThrow(() -> new SQLException("Document not found with id: " + id));
        return documentMapper.toDto(entity);
    }

}
