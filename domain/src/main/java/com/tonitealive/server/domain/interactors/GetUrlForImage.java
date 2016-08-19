package com.tonitealive.server.domain.interactors;

import com.tonitealive.server.domain.repositories.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUrlForImage {

    private final FilesRepository filesRepository;

    @Autowired
    public GetUrlForImage(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public String execute(String fileId, int width, int height) {
        return filesRepository.getUrlForImage(fileId, width, height);
    }

}
