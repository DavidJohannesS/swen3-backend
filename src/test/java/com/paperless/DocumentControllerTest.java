package com.paperless;

import com.paperless.controller.DocumentController;
import com.paperless.model.Document;
import com.paperless.repository.DocumentEntity;
import com.paperless.service.DocumentMapper;
import com.paperless.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    private Document sampleDto;
    private DocumentEntity sampleEntity;

    @BeforeEach
    void setUp() {

        sampleDto = Document.builder()
                .id(1L)
                .title("Sample Document")
                .description("This is a sample document.")
                .createdAt(Instant.parse("2023-10-01T12:00:00Z"))
                .build();

        sampleEntity = new DocumentEntity();
        sampleEntity.setTitle("Sample Document");
        sampleEntity.setDescription("This is a sample document.");
        sampleEntity.setCreatedAt(Instant.parse("2023-10-01T12:00:00Z"));
    }

    @Test
    void getDocumentById_ReturnsDocument() throws SQLException {
        Long id = 1L;

        when(documentService.getDocumentById(anyLong())).thenReturn(sampleDto);

        Document result = documentController.getById(id);

        assertThat(result).isEqualTo(sampleDto);
        verify(documentService).getDocumentById(id);
    }

    @Test
    void getDocumentById_ReturnsNotFound() throws SQLException {
        Long id = 999L;
        when(documentService.getDocumentById(anyLong())).thenReturn(null);

        assertThatThrownBy(() -> documentController.getById(id))
                .isInstanceOf((ResponseStatusException.class))
                .satisfies(ex -> {
                    var rse = (ResponseStatusException) ex;
                    assertThat(rse.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
                });
        verify(documentService).getDocumentById(anyLong());
    }

    @Test
    void createDocument_ReturnsCreatedDocument() throws SQLException {
        Document request = Document.builder()
                .title("New Doc")
                .description("Desc")
                .createdAt(Instant.parse("2023-10-01T12:00:00Z"))
                .build();

        documentController.createDocument(request);

        verify(documentService).create(request);
    }

}