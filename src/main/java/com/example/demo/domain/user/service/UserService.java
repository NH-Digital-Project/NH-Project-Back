package com.example.demo.domain.user.service;


import com.example.demo.domain.user.dto.response.MyInfoResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;


    public MyInfoResponse getMyInfo(Long userId) {
      
        User user = getOrElseThrow(userId);

        return MyInfoResponse.from(user);
    }


    @Transactional
    public void deleteMe(Long userId) {

        User user = getOrElseThrow(userId);

        user.withdraw();

        // Todo 지원서 제출 상태에서도 탈퇴 가능 여부 확인
    }

    private User getOrElseThrow(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}