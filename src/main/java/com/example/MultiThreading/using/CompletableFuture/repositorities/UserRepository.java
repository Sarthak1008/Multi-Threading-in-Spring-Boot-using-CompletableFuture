package com.example.MultiThreading.using.CompletableFuture.repositorities;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MultiThreading.using.CompletableFuture.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
