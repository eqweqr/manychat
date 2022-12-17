package com.example.webchatter.repository;


import com.example.webchatter.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByLogin(String login);

    boolean existsByLogin(String login);

}