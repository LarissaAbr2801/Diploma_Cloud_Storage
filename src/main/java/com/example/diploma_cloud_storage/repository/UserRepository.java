package com.example.diploma_cloud_storage.repository;

import com.example.diploma_cloud_storage.entity.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAuthToken(String authToken);

    Optional<User> findByLogin(String login);
}

