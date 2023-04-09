package com.example.diploma_cloud_storage.service;

import com.example.diploma_cloud_storage.entity.File;
import com.example.diploma_cloud_storage.entity.User;
import com.example.diploma_cloud_storage.model.Login;
import com.example.diploma_cloud_storage.repository.FileRepository;
import com.example.diploma_cloud_storage.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CloudStorageService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    public CloudStorageService(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    public void uploadFile(String authToken, String fileName, MultipartFile multipartFile) {
        File file = File.builder()
                .size(multipartFile.getSize())
                .name(fileName)
                .build();
    }

    public void deleteFile(String authToken, String fileName) {

    }

    public File getFile(String authToken, String fileName) {
        return null;
    }

    public void putFile(String authToken, String fileName, MultipartFile file) {

    }


    public List<File> getAllFiles(String authToken, Integer limit) {
        return fileRepository.findAllByUser(new User()).stream().collect(Collectors.toList());
    }


}
