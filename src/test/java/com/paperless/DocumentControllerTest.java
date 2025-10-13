package com.paperless;

import com.paperless.controller.DocumentController;
import com.paperless.model.Document;
import com.paperless.repository.DocumentEntity;
import com.paperless.service.DocumentMapper;
import com.paperless.service.DocumentService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Mock
    private DocumentService documentService;

    @Mock
    private DocumentMapper documentMapper;

    @InjectMocks
    private DocumentController documentController;

    private Document sampleDto;
    private DocumentEntity sampleEntity;

    @BeforeEach
    void setUp() {

        sampleDto = Document.builder()
                .id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .title("Sample Document")
                .description("This is a sample document.")
                .createdAt(Instant.parse("2023-10-01T12:00:00Z"))
                .build();

        sampleEntity = new DocumentEntity();
        sampleEntity.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        sampleEntity.setTitle("Sample Document");
        sampleEntity.setDescription("This is a sample document.");
        sampleEntity.setCreatedAt(Instant.parse("2023-10-01T12:00:00Z"));
    }

    @Test
    void create_returns_201_and_body() throws Exception {
        // Arrange
        var document = sampleDto;

        // Act & Assert
        mockMvc.perform(post("/api/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "123e4567-e89b-12d3-a456-426614174000",
                        "title": "Sample Document",
                        "description": "This is a sample document.",
                        "createdAt": "2023-10-01T12:00:00Z"
                    }
                """))
                .andExpect(status().isCreated());

        verify(documentService).create(document);
    }




}
