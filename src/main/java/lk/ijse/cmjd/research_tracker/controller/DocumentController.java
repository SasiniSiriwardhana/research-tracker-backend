package lk.ijse.cmjd.research_tracker.controller;

import jakarta.validation.Valid;
import lk.ijse.cmjd.research_tracker.common.ApiResponse;
import lk.ijse.cmjd.research_tracker.dto.DocumentDTO;
import lk.ijse.cmjd.research_tracker.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for document management endpoints.
 * Documents are scoped to projects.
 */
@RestController
public class DocumentController {

    private final DocumentService documentService;

    /**
     * Constructor injection of DocumentService.
     */
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * GET /api/projects/{id}/documents - List documents for a project.
     *
     * @param id the project ID
     * @return list of document DTOs
     */
    @GetMapping("/api/projects/{id}/documents")
    public ResponseEntity<ApiResponse<List<DocumentDTO>>> getDocumentsByProject(
            @PathVariable String id
    ) {
        List<DocumentDTO> documents = documentService.getDocumentsByProject(id);
        return ResponseEntity.ok(ApiResponse.success("Documents retrieved successfully", documents));
    }

    /**
     * POST /api/projects/{id}/documents - Upload a new document to a project.
     * The uploader is automatically set to the authenticated user.
     *
     * @param id             the project ID
     * @param documentDTO    the document data
     * @param authentication the current authentication context
     * @return created document DTO
     */
    @PostMapping("/api/projects/{id}/documents")
    public ResponseEntity<ApiResponse<DocumentDTO>> createDocument(
            @PathVariable String id,
            @Valid @RequestBody DocumentDTO documentDTO,
            Authentication authentication
    ) {
        DocumentDTO created = documentService.createDocument(id, documentDTO, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Document uploaded successfully", created));
    }

    /**
     * DELETE /api/documents/{id} - Delete a document (Admin or PI only).
     *
     * @param id the document ID
     * @return success message
     */
    @DeleteMapping("/api/documents/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteDocument(@PathVariable String id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok(ApiResponse.success("Document deleted successfully"));
    }
}
