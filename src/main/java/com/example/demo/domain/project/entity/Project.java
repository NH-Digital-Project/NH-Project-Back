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

    @Builder
    private Project(Long id, Application application, String farmName, String productCategory,
        String thumbnailImageUrl, String description, ProjectStatus projectStatus,
        String happyBeanUrl) {
        this.id = id;
        this.application = application;
        this.farmName = farmName;
        this.productCategory = productCategory;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.description = description;
        this.projectStatus = projectStatus;
        this.happyBeanUrl = happyBeanUrl;
    }

    public void update(String farmName, String productCategory,
                                  String thumbnailImageUrl, String description,
                                  ProjectStatus projectStatus, String happyBeanUrl) {
        if (farmName != null) this.farmName = farmName;
        if (productCategory != null) this.productCategory = productCategory;
        if (thumbnailImageUrl != null) this.thumbnailImageUrl = thumbnailImageUrl;
        if (description != null) this.description = description;
        if (projectStatus != null) this.projectStatus = projectStatus;
        if (happyBeanUrl != null) this.happyBeanUrl = happyBeanUrl;
    }
}

