package com.example.demo.domain.project.repository;

import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 해당 지원서로 등록된 선정업체가 있는지 확인(중복 검증)
    boolean existsByApplicationId(Long applicationId);

    // 전체 선정업체를 상태 우선순위 및 최신 등록순으로 조회
    @Query("SELECT p FROM Project p " +
            "ORDER BY CASE p.projectStatus " +
            "WHEN 'IN_PROGRESS' THEN 1 " +
            "WHEN 'BEFORE_PROGRESS' THEN 2 " +
            "WHEN 'COMPLETED' THEN 3 ELSE 4 END, " +
            "p.createdAt DESC")
    List<Project> findAllWithCustomOrder();

    // status 필터링이 있을 때
    List<Project> findByProjectStatusOrderByCreatedAtDesc(ProjectStatus status);
}
