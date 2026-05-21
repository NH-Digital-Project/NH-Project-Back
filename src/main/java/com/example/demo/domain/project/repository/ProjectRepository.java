package com.example.demo.domain.project.repository;

import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 해당 지원서로 등록된 선정업체가 있는지 확인(중복 검증)
    boolean existsByApplicationId(Long applicationId);

    // 전체 선정업체를 최신 등록순으로 조회
    List<Project> findAllByOrderByCreatedAtDesc();

    // status 필터링이 있을 때
    List<Project> findByProjectStatusOrderByCreatedAtDesc(ProjectStatus status);
}
