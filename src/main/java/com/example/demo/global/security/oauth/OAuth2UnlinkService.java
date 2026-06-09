package com.example.demo.global.security.oauth;

import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UnlinkService {

    private static final String NAVER_TOKEN_URL = "https://nid.naver.com/oauth2.0/token";

    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String SERVICE_PROVIDER = "service_provider";

    private static final String DELETE = "delete";
    private static final String NAVER = "NAVER";


    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    public void unlinkNaverAccount(String naverAccessToken) {

        String uriString = createUnlinkUri(naverAccessToken);

        try {
            requestUnlink(uriString);
        } catch (Exception e) {
            log.error("네이버 회원 탈퇴 요청 중 예외가 발생했습니다.", e);
            throw new CustomException(ErrorCode.NAVER_UNLINK_FAILED);
        }
    }

    private String createUnlinkUri(String naverAccessToken) {
        return UriComponentsBuilder.fromUriString(NAVER_TOKEN_URL)
            .queryParam(GRANT_TYPE, DELETE)
            .queryParam(CLIENT_ID, naverClientId)
            .queryParam(CLIENT_SECRET, naverClientSecret)
            .queryParam(ACCESS_TOKEN, naverAccessToken)
            .queryParam(SERVICE_PROVIDER, NAVER)
            .build()
            .toUriString();
    }

    private void requestUnlink(String uriString) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response =
            restTemplate.getForEntity(uriString, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(ErrorCode.NAVER_UNLINK_FAILED);
        }
    }

}
