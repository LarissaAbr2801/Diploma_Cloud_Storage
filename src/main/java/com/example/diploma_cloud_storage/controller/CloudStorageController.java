package com.example.diploma_cloud_storage.controller;

import com.example.diploma_cloud_storage.constant.SecurityConstants;
import com.example.diploma_cloud_storage.entity.File;
import com.example.diploma_cloud_storage.entity.User;
import com.example.diploma_cloud_storage.model.Login;
import com.example.diploma_cloud_storage.security.service.AuthorizationServer;
import com.example.diploma_cloud_storage.service.CloudStorageService;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Abramova Larisa
 */
@RestController
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequestMapping("/cloud")
public class CloudStorageController {
    private final CloudStorageService cloudStorageService;

    private final AuthorizationServer authorizationServer;

    public CloudStorageController(CloudStorageService cloudStorageService,
                                  AuthorizationServer authorizationServer) {
        this.cloudStorageService = cloudStorageService;
        this.authorizationServer = authorizationServer;
    }

    /**
     * Authorization method
     *
     * @param user - Login and password hash
     * @return auth-token
     */
    @PostMapping("/login")
    public Login login(@RequestBody User user) {
        System.out.println(user);
        return authorizationServer.login(user, user.getLogin());

    }

    /**
     * description: Logout
     *
     * @param authToken - user's authorization token
     */
    @PostMapping("/logout")
    public void logout(@RequestHeader(SecurityConstants.AUTH_TOKEN) String authToken) {
        authorizationServer.logout(authToken);
    }

    /**
     * description: Upload file to server
     *
     * @param authToken - user's authorization token
     * @param fileName  - File's name to upload
     * @param file      - File to upload
     */
    @PostMapping(value = "/upload-file", produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadFile(@RequestHeader(SecurityConstants.AUTH_TOKEN) String authToken,
                           @RequestParam String fileName,
                           @RequestBody MultipartFile file) {
        cloudStorageService.uploadFile(authToken, fileName, file);

    }

    /**
     * description: Delete file
     *
     * @param authToken - user's authorization token
     * @param fileName  - File name to delete
     */
    @DeleteMapping("/delete-file")
    public void deleteFile(@RequestHeader(SecurityConstants.AUTH_TOKEN) String authToken,
                           @PathVariable String fileName) {
        cloudStorageService.deleteFile(authToken, fileName);
    }

    /**
     * description: Dowload file from cloud
     *
     * @param authToken - user's authorization token
     * @param fileName  - File name to delete
     * @return file
     */
    @GetMapping("/get-file")
    public File getFile(@RequestHeader(SecurityConstants.AUTH_TOKEN) String authToken,
                        @PathVariable String fileName) {

        return cloudStorageService.getFile(authToken, fileName);
    }

    /**
     * description: Upload file
     *
     * @param authToken - user's authorization token
     * @param fileName  - File name to upload
     */
    @PutMapping("/put-file")
    public void putFile(@RequestHeader(SecurityConstants.AUTH_TOKEN) String authToken,
                        @PathVariable String fileName,
                        @RequestBody MultipartFile file) {

        cloudStorageService.putFile(authToken, fileName, file);
    }

    /**
     * description: Get all files
     *
     * @param authToken - user's authorization token
     * @param limit     - Number requested items
     * @return list of files
     */
    @GetMapping("/list-files")
    public List<File> listFiles(@RequestHeader(SecurityConstants.AUTH_TOKEN) String authToken,
                                Integer limit) {
        return cloudStorageService.getAllFiles(authToken, limit);
    }

}
