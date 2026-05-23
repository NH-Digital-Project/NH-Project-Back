package com.example.demo.domain.application.repository;

import com.example.demo.domain.application.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByUserId(Long userId);

    // 전달받은 날짜(today)로 시작하는 지원서 번호를 내림차순으로 정렬하여 가장 최근 번호 조회
    Optional<Application> findTopByApplicationNumberStartingWithOrderByApplicationNumberDesc(
        String today);

    Optional<Application> findByUserId(Long userId);

    Page<Application> findByUserNameContainingOrFarmNameContaining(String userName, String farmName,
        Pageable pageable);

    // 유저의 유효한(삭제되지 않은) 지원서 존재 여부 확인으로 변경
    boolean existsByUserIdAndDeletedAtIsNull(Long userId);

    // 유저의 유효한(삭제되지 않은) 지원서 조회로 변경
    Optional<Application> findByUserIdAndDeletedAtIsNull(Long userId);
}
