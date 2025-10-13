package com.paperless.service;

import com.paperless.model.Document;
import com.paperless.repository.DocumentEntity;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class DocumentMapper extends AbstractMapper<DocumentEntity, Document> {

    @Override
    public Document toDto(DocumentEntity entity) throws SQLException {
        if (entity == null) {
            return null;
        }
        return Document.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public DocumentEntity toEntity(Document dto) throws SQLException {
        if (dto == null) {
            return null;
        }
        return DocumentEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
