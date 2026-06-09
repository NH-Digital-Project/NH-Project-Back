package com.example.demo.domain.user.service;


import com.example.demo.domain.user.dto.request.UpdatePhoneNumberRequest;
import com.example.demo.domain.user.dto.request.UpdateUserNameRequest;
import com.example.demo.domain.user.dto.response.MyInfoResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import com.example.demo.global.security.oauth.OAuth2UnlinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final OAuth2UnlinkService oAuth2UnlinkService;


    public MyInfoResponse getMyInfo(Long userId) {

        User user = getOrElseThrow(userId);

        return MyInfoResponse.from(user);
    }


    @Transactional
    public void deleteMe(Long userId) {

        User user = getOrElseThrow(userId);

        user.withdraw();
        oAuth2UnlinkService.unlinkNaverAccount(user.getNaverAccessToken());
    }

    @Transactional
    public MyInfoResponse updateMyName(Long userId, UpdateUserNameRequest request) {

        User user = getOrElseThrow(userId);
        user.updateUserName(request.userName());

        return MyInfoResponse.from(user);
    }

    @Transactional
    public MyInfoResponse updatePhoneNumber(Long userId, UpdatePhoneNumberRequest request) {

        User user = getOrElseThrow(userId);
        user.updatePhoneNumber(request.phoneNumber());

        return MyInfoResponse.from(user);
    }

    private User getOrElseThrow(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}