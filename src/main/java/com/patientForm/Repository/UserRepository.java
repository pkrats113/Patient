package com.patientForm.Repository;

import com.patientForm.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User,Long> {


    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username,String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
