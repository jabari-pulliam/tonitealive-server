package com.tonitealive.server.web.controllers;

import com.tonitealive.server.domain.exceptions.InternalServerErrorException;
import com.tonitealive.server.domain.interactors.GetUrlForImage;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
                                 @RequestParam("height") int viewportHeight, HttpServletRequest request) {
        // We need to decode the file ID since it may contain unsafe characters
        String decodedFileId;
        try {
            decodedFileId = URLDecoder.decode(fileId, request.getCharacterEncoding());
        } catch (UnsupportedEncodingException ex) {
            log.error("Character encoding not supported by this platform", ex);
            throw InternalServerErrorException.create();
        }

        return getUrlForImage.execute(decodedFileId, viewportWidth, viewportHeight);
    }

}
