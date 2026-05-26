package com.example.demo.domain.project.entity;

import com.example.demo.domain.application.entity.Application;
import com.example.demo.global.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "projects")
@Entity
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", unique = true)
    private Application application;

    private String farmName;

    private String productCategory;

    private String thumbnailImageUrl;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    private String happyBeanUrl;

    private Integer sortOrder; // 순서 저장 필드 추가

    @Builder
    private Project(Long id, Application application, String farmName, String productCategory,
        String thumbnailImageUrl, String description, ProjectStatus projectStatus,
        String happyBeanUrl, Integer sortOrder) {
        this.id = id;
        this.application = application;
        this.farmName = farmName;
        this.productCategory = productCategory;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.description = description;
        this.projectStatus = projectStatus;
        this.happyBeanUrl = happyBeanUrl;
        this.sortOrder = sortOrder != null ? sortOrder : 0; // 기본값 0
    }

    public void update(String farmName, String productCategory,
                                  String thumbnailImageUrl, String description,
                                  ProjectStatus projectStatus, String happyBeanUrl) {
        if (farmName != null) this.farmName = farmName;
        if (productCategory != null) this.productCategory = productCategory;
        if (thumbnailImageUrl != null) {
            // isBlank()는 문자열이 비어있거나 공백만 있을 때 true를 반환
            // 비어있다면 null로 저장하고, 값이 있다면 그 값을 저장하도록 작성
            this.thumbnailImageUrl = thumbnailImageUrl.isBlank() ? null : thumbnailImageUrl;
        }
        if (description != null) this.description = description;
        if (projectStatus != null) this.projectStatus = projectStatus;
        if (happyBeanUrl != null) {
            this.happyBeanUrl = happyBeanUrl.isBlank() ? null : happyBeanUrl;
        }
    }

    // 순서 변경 메서드
    public void updateSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}

