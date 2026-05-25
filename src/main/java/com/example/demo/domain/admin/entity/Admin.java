package com.example.demo.domain.admin.entity;

import com.example.demo.global.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admins")
@Entity
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String adminName;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    private Admin(Long id, String loginId, String password , String adminName) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.adminName = adminName;
        this.role = Role.ROLE_ADMIN;
    }
}

