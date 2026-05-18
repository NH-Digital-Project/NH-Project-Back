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

    private String naverId;

    private String naverName;

    private Boolean applied;

    private User(String naverId, String naverName, Boolean applied) {
        this.naverId = naverId;
        this.naverName = naverName;
        this.applied = applied;
    }

    public void withdraw() {
        this.delete();
    }
  
    public static User create(String naverId, String naverName) {
        return new User(naverId, naverName, false);
    }
}
