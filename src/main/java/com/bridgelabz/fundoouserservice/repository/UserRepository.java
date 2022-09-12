package com.bridgelabz.fundoouserservice.repository;

import com.bridgelabz.fundoouserservice.model.UserServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserServiceModel, Long> {
    Optional<UserServiceModel> findByEmailId(String emailId);
}
