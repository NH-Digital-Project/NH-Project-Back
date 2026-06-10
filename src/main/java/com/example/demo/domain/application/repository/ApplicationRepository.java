package com.example.demo.domain.application.repository;

import com.example.demo.domain.application.entity.Application;
import com.example.demo.domain.application.status.ApplicationStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // 전달받은 날짜(today)로 시작하는 지원서 번호를 내림차순으로 정렬하여 가장 최근 번호 조회
    Optional<Application> findTopByApplicationNumberStartingWithOrderByApplicationNumberDesc(
        String today);

    Page<Application> findByUserNameContainingOrBusinessNameContaining(String userName, String businessName,
        Pageable pageable);

    // 유저의 유효한(삭제되지 않은) 지원서 존재 여부 확인으로 변경
    boolean existsByUserIdAndDeletedAtIsNull(Long userId);

    // 유저의 유효한(삭제되지 않은) 지원서 조회로 변경
    Optional<Application> findByUserIdAndDeletedAtIsNull(Long userId);

    List<Application> findByStatus(ApplicationStatus applicationStatus);

    // 여러 유저 ID를 한 번에 조회하여 CANCELED 상태가 아닌 지원서가 존재하는 유저 ID 목록 반환
    @Query("SELECT a.user.id FROM Application a WHERE a.user.id IN :userIds AND a.status <> :status")
    List<Long> findUserIdsByUserIdInAndStatusNot(@Param("userIds") List<Long> userIds, @Param("status") ApplicationStatus status);

    // 상태 목록에 따른 조회
    Page<Application> findByStatusIn(List<ApplicationStatus> statuses, Pageable pageable);

    // 검색어와 상태 목록을 동시에 적용하는 조회
    @Query("SELECT a FROM Application a WHERE " +
            "(a.userName LIKE %:keyword% OR a.businessName LIKE %:keyword%) " +
            "AND a.status IN :statuses")
    Page<Application> findByKeywordAndStatuses(
            @Param("keyword") String keyword,
            @Param("statuses") List<ApplicationStatus> statuses,
            Pageable pageable);
}
