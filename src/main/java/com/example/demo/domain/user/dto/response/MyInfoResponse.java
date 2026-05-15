package com.example.demo.domain.user.dto.response;

import com.example.demo.domain.user.entity.User;
import java.time.LocalDateTime;

public record MyInfoResponse (
    Long userId,
    String naverId,
    String naverName,
    LocalDateTime createdAt
) {

    public static MyInfoResponse from(User user) {
        return new MyInfoResponse(user.getId(), user.getNaverId(), user.getNaverName(),
            user.getCreatedAt());
    }

}
