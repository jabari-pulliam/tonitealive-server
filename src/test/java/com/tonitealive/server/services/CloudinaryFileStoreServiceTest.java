package com.tonitealive.server.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloudinaryFileStoreServiceTest {

    private CloudinaryFileStoreService fileStoreService;

    @Autowired
    private Environment environment;

    @Autowired
    private WebApplicationContext context;

    private Cloudinary cloudinary;

    private File imageFile;
    private String rootFolder;

    @Before
    public void setup() throws Exception {
        fileStoreService = new CloudinaryFileStoreService(environment);
        cloudinary = new Cloudinary(environment.getProperty("CLOUDINARY_URL"));
        rootFolder = environment.getProperty("tonitealive.filestoreservice.rootFolder");

        Resource resource = context.getResource("classpath:images/test_image.jpg");
        imageFile = resource.getFile();
    }

    @After
    public void tearDown() throws Exception {
        // Cleanup all of our test images
        Map options = ObjectUtils.asMap("invalidate", true);
        cloudinary.api().deleteResourcesByPrefix(rootFolder, options);
    }

    @Test
    public void storeImage_shouldReturnPublicId() {
        // When
        String publicId = fileStoreService.storeImage(imageFile);

        // Then
        assertThat(publicId).isNotNull().isNotEmpty();
    }

    @Test
    public void getUrlForImage_shouldReturnUrlPointingToScaledImage() throws Exception {
        // With
        int width = 100;
        int height = 120;

        // When
        String publicId = fileStoreService.storeImage(imageFile);
        String url = fileStoreService.getUrlForImage(publicId, width, height);

        // Then
        assertThat(publicId).isNotNull().isNotEmpty();
        assertThat(url).isNotNull().isNotEmpty();
        Resource imageRes = new UrlResource(url);
        BufferedImage image = ImageIO.read(imageRes.getURL());
        assertThat(image.getWidth()).isLessThanOrEqualTo(width);
        assertThat(image.getHeight()).isLessThanOrEqualTo(height);
    }

    @Test
    public void getUrlForFile_shouldReturnUrl() throws Exception {
        // When
        String publicId = fileStoreService.storeImage(imageFile);
        String url = fileStoreService.getUrlForFile(publicId);

        // Then
        assertThat(publicId).isNotNull().isNotEmpty();
        assertThat(url).isNotNull().isNotEmpty();
        Resource imageRes = new UrlResource(url);
        assertThat(imageRes.contentLength()).isGreaterThan(0);
    }

    @Test
    public void deleteFile_shouldMakeFileUnavailable() throws Exception {
        // Given
        Map result = cloudinary.uploader().upload(imageFile, ObjectUtils.emptyMap());
        String publicId = (String) result.get("public_id");

        // When
        fileStoreService.deleteFile(publicId, true);

        // Then
        String url = cloudinary.url().generate(publicId);
        Resource imageRes = new UrlResource(url);
        assertThat(imageRes.contentLength()).isEqualTo(0);
    }

}
