package com.example.SpringClinicPet.repository;

import com.example.SpringClinicPet.model.User;
import com.example.SpringClinicPet.model.enums.UserRole;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByLogin(String login);

    Optional<User> findUserByLogin(String login);

    @Query("SELECT u FROM User u WHERE u.role = :role AND u.enabled = false")
    List<User> findUsersByRoleAndEnabledFalse(@Param("role") UserRole role);

    @Query("SELECT u FROM User u WHERE u.role = :role AND u.enabled = true")
    List<User> findUsersByRoleAndEnabledTrue(@Param("role") UserRole role);


}
