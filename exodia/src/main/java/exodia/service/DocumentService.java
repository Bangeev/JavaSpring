package exodia.service;

import exodia.domain.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {

    DocumentServiceModel scheduleDocument(DocumentServiceModel documentServiceModel);
    DocumentServiceModel findDocumentById(String id);

    List<DocumentServiceModel> findAllDocuments();

    boolean printDocumentById(String id);

}
