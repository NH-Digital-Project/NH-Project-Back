package com.example.demo.domain.user.entity;

import com.example.demo.global.common.entity.BaseSoftDeleteEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL") // 모든 select 쿼리 시 삭제 안 된 것만 조회
@Table(name = "users")
@Entity
public class User extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    private String userName;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private Boolean applied;

    private User(String oauthId, Role role, Boolean applied) {
        this.oauthId = oauthId;
        this.role = role;
        this.applied = applied;
    }

    public void withdraw() {
        this.delete();
    }

    public static User createUser(String oauthId, String userName, String email) {
        return new User(oauthId, userName, email, Role.ROLE_USER, false);
    }
}
