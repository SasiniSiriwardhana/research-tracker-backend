package lk.ijse.cmjd.research_tracker.service;

import lk.ijse.cmjd.research_tracker.dto.DocumentDTO;

import java.util.List;

/**
 * Service interface for document management operations.
 */
public interface DocumentService {

    /**
     * Get all documents for a specific project.
     *
     * @param projectId the project ID
     * @return list of document DTOs
     */
    List<DocumentDTO> getDocumentsByProject(String projectId);

    /**
     * Upload a new document to a project.
     *
     * @param projectId   the project ID
     * @param documentDTO the document data
     * @param username    the username of the uploader
     * @return created document DTO
     */
    DocumentDTO createDocument(String projectId, DocumentDTO documentDTO, String username);

    /**
     * Delete a document by its ID.
     *
     * @param id the document ID
     */
    void deleteDocument(String id);
}
