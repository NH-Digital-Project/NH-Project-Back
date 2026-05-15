package com.example.demo.domain.user.dto.request;

import com.example.demo.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyInfoResponse {

    private final Long userId;

    private final String naverId;

    private final String naverName;

    private final LocalDateTime createdAt;

    public static MyInfoResponse from(User user) {
        return new MyInfoResponse(user.getId(), user.getNaverId(), user.getNaverName(),
            user.getCreatedAt());
    }
}
