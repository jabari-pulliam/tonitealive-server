package com.tonitealive.server.web.controllers;

import com.tonitealive.server.domain.interactors.GetUrlForImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilesController {

    private final GetUrlForImage getUrlForImage;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public FilesController(GetUrlForImage getUrlForImage) {
        this.getUrlForImage = getUrlForImage;
    }

    @GetMapping("/images")
    public String getUrlForImage(@RequestParam("fileId") String fileId,
                                 @RequestParam("width") int viewportWidth,
                                 @RequestParam("height") int viewportHeight) {
        return getUrlForImage.execute(fileId, viewportWidth, viewportHeight);
    }

}
