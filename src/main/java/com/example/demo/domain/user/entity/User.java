package com.example.demo.domain.user.entity;

import com.example.demo.global.common.entity.BaseSoftDeleteEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?") // repository.delete() 방지
@SQLRestriction("deleted_at IS NULL") // 모든 select 쿼리 시 삭제 안 된 것만 조회
@Table(name = "users")
@Entity
public class User extends BaseSoftDeleteEntity {

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

    public void withdraw() {
        this.delete();
    }
}
