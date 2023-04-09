package com.example.diploma_cloud_storage.repository;

import com.example.diploma_cloud_storage.entity.File;
import com.example.diploma_cloud_storage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    @Override
    File save(File entity);

    List<File> findAllByUser(User user);
}
