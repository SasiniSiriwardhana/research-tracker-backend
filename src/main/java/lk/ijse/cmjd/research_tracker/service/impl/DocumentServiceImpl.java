package lk.ijse.cmjd.research_tracker.service.impl;

import lk.ijse.cmjd.research_tracker.dto.DocumentDTO;
import lk.ijse.cmjd.research_tracker.entity.Document;
import lk.ijse.cmjd.research_tracker.entity.Project;
import lk.ijse.cmjd.research_tracker.entity.User;
import lk.ijse.cmjd.research_tracker.exception.ResourceNotFoundException;
import lk.ijse.cmjd.research_tracker.repository.DocumentRepository;
import lk.ijse.cmjd.research_tracker.repository.ProjectRepository;
import lk.ijse.cmjd.research_tracker.repository.UserRepository;
import lk.ijse.cmjd.research_tracker.service.DocumentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of DocumentService.
 * Handles document upload and deletion operations scoped to projects.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    /**
     * Constructor injection of repositories.
     */
    public DocumentServiceImpl(
            DocumentRepository documentRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.documentRepository = documentRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all documents for a specific project.
     */
    @Override
    public List<DocumentDTO> getDocumentsByProject(String projectId) {
        // Verify project exists
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }

        return documentRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Upload a new document to a project.
     * Associates the document with the authenticated user as uploader.
     */
    @Override
    public DocumentDTO createDocument(String projectId, DocumentDTO documentDTO, String username) {
        // Find the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));

        // Find the uploader user by username
        User uploader = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Build and save the document entity
        Document document = Document.builder()
                .project(project)
                .title(documentDTO.getTitle())
                .description(documentDTO.getDescription())
                .urlOrPath(documentDTO.getUrlOrPath())
                .uploadedBy(uploader)
                .build();

        Document savedDocument = documentRepository.save(document);
        return mapToDTO(savedDocument);
    }

    /**
     * Delete a document by its ID.
     */
    @Override
    public void deleteDocument(String id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "id", id));
        documentRepository.delete(document);
    }

    /**
     * Map a Document entity to a DocumentDTO.
     */
    private DocumentDTO mapToDTO(Document document) {
        return DocumentDTO.builder()
                .id(document.getId())
                .projectId(document.getProject().getId())
                .title(document.getTitle())
                .description(document.getDescription())
                .urlOrPath(document.getUrlOrPath())
                .uploadedById(document.getUploadedBy().getId())
                .uploadedByName(document.getUploadedBy().getFullName())
                .uploadedAt(document.getUploadedAt())
                .build();
    }
}
