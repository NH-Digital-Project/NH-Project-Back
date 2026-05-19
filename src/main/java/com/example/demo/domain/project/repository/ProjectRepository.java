package com.example.demo.domain.project.repository;

import com.example.demo.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 해당 지원서로 등록된 선정업체가 있는지 확인(중복 검증)
    boolean existsByApplicationId(Long applicationId);
}
