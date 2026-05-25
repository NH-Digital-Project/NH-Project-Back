package com.example.demo.domain.user.repository;

import com.example.demo.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthId(String oauthId);

    Page<User> findByUserNameContainingOrEmailContaining(String userName, String email, Pageable pageable);
}
