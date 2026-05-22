package com.example.demo.domain.admin.repository;

import com.example.demo.domain.admin.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByLoginId(String loginId);

    Optional<Admin> findByLoginId(String id);
}
