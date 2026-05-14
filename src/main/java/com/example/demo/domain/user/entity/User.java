package com.example.demo.domain.user.entity;

import com.example.demo.global.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naverId;

    private String naverName;

    private Boolean applied;


    private User(Long id, String naverId, String naverName, Boolean applied) {
        this.id = id;
        this.naverId = naverId;
        this.naverName = naverName;
        this.applied = applied;
    }
}
