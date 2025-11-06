package com.java.be_ecommerce_app.repository;

import com.java.be_ecommerce_app.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
    Optional<User> findByUserId(Long userId);


    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    User findByEmail(String email);
}