package com.tonitealive.server.data.repositories;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Future;

public interface FilesRepository {

    Future<String> storeImageFile(MultipartFile file);

}
