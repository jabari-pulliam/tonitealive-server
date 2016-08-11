package com.tonitealive.server.data.repositories;

import com.tonitealive.server.data.entities.FileNode;
import com.tonitealive.server.data.entities.UserProfileNode;
import com.tonitealive.server.domain.exceptions.ResourceNotFoundException;
import com.tonitealive.server.domain.models.FileType;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.domain.repositories.FilesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class DefaultUserProfilesRepositoryTest {

    @MockBean
    private UserProfileNodesRepository mockUserProfileNodesRepository;

    @MockBean
    private FileNodesRepository mockFileNodesRepository;

    @MockBean
    private FilesRepository mockFilesRepository;

    @MockBean
    private ConversionService mockConversionService;

    private DefaultUserProfilesRepository userProfilesRepository;

    @Before
    public void setup() {
        userProfilesRepository = new DefaultUserProfilesRepository(mockUserProfileNodesRepository,
                mockFileNodesRepository, mockFilesRepository, mockConversionService);
    }

    @Test
    public void save_shouldSaveProfile() {
        // Given
        String username = "username";
        String email = "email";
        UserProfile profile = UserProfile.create(username, email);
        UserProfileNode profileNode = new UserProfileNode(username, email);

        given(mockConversionService.convert(profile, UserProfileNode.class)).willReturn(profileNode);

        // When
        userProfilesRepository.save(profile);

        // Then
        verify(mockUserProfileNodesRepository).save(profileNode);
    }

    @Test
    public void delete_shouldDeleteProfile() {
        // Given
        String username = "username";
        String email = "email";
        UserProfileNode profileNode = new UserProfileNode(username, email);

        given(mockUserProfileNodesRepository.findByUsername(username)).willReturn(profileNode);

        // When
        userProfilesRepository.delete(username);

        // Then
        verify(mockUserProfileNodesRepository).delete(profileNode);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void delete_withUnknownUsername_shouldThrowResourceNotFound() {
        // Given
        String username = "username";

        given(mockUserProfileNodesRepository.findByUsername(username)).willReturn(null);

        // When
        userProfilesRepository.delete(username);
    }

    @Test
    public void getByUsername_shouldReturnProfile() {
        // Given
        String username = "username";
        String email = "email";
        UserProfile profile = UserProfile.create(username, email);
        UserProfileNode profileNode = new UserProfileNode(username, email);

        given(mockConversionService.convert(profileNode, UserProfile.class)).willReturn(profile);
        given(mockUserProfileNodesRepository.findByUsername(username)).willReturn(profileNode);

        // When
        UserProfile returnedProfile = userProfilesRepository.getByUsername(username);

        // Then
        assertThat(returnedProfile).isEqualTo(profile);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getByUsername_withUnknownUsername_shouldThrowResourceNotFound() {
        // Given
        String username = "username";

        given(mockUserProfileNodesRepository.findByUsername(username)).willReturn(null);

        // When
        userProfilesRepository.getByUsername(username);
    }

    @Test
    public void updateUserProfilePhoto_shouldSavePhotoFile() throws IOException {
        // Given
        String username = "username";
        String email = "email";
        UserProfileNode profileNode = new UserProfileNode(username, email);
        String fileId = "1234";
        FileNode fileNode = new FileNode(fileId);
        File dummyFile = mock(File.class);

        given(mockUserProfileNodesRepository.findByUsername(username)).willReturn(profileNode);
        given(mockFileNodesRepository.save(any(FileNode.class))).willReturn(fileNode);
        given(mockFilesRepository.storeFile(dummyFile, FileType.IMAGE)).willReturn(fileId);

        // When
        userProfilesRepository.updateUserProfilePhoto(username, dummyFile);

        // Then
        verify(mockFilesRepository).storeFile(dummyFile, FileType.IMAGE);
        verify(mockFileNodesRepository).save(any(FileNode.class));
        ArgumentCaptor<UserProfileNode> profileNodeCaptor = ArgumentCaptor.forClass(UserProfileNode.class);
        verify(mockUserProfileNodesRepository).save(profileNodeCaptor.capture());
        UserProfileNode updatedProfileNode = profileNodeCaptor.getValue();
        assertThat(updatedProfileNode.getProfilePhoto()).isNotNull();
        assertThat(updatedProfileNode.getProfilePhoto().getFileId()).isEqualTo(fileNode.getFileId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateUserProfile_withUnknownUsername_shouldThrowResourceNotFound() {
        // With
        String username = "username";
        File dummyFile = mock(File.class);

        given(mockUserProfileNodesRepository.findByUsername(username)).willReturn(null);

        // When
        userProfilesRepository.updateUserProfilePhoto(username, dummyFile);
    }

}
