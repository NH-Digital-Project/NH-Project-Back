package com.example.demo.domain.application.service;

import com.example.demo.domain.application.dto.request.ApplicationReqDto;
import com.example.demo.domain.application.dto.response.ApplicationResDto;
import com.example.demo.domain.application.dto.response.ApplicationStatusResDto;
import com.example.demo.domain.application.dto.response.CreateApplicationResDto;
import com.example.demo.domain.application.entity.Address;
import com.example.demo.domain.application.entity.Application;
import com.example.demo.domain.application.repository.ApplicationRepository;
import com.example.demo.domain.application.status.ApplicationStatus;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    // 임의로 사업 신청 시간 설정
    private static final LocalDateTime APPLICATION_START_TIME = LocalDateTime.of(2026, 5, 1, 0, 0);
    private static final LocalDateTime APPLICATION_END_TIME = LocalDateTime.of(2026,12,31,23,59);

    @Transactional
    public CreateApplicationResDto createApplication(Long userId, ApplicationReqDto request) {

        // 사업 신청 기간 및 생년월일 검증
        validateApplicationPeriod();
        validateBirthDate(request.getBirthDate());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 계정당 1번 지원 검증
        if(applicationRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.APPLICATION_ALREADY_EXISTS);
        }

        // 카카오 주소 API를 통해 입력받은 주소 정보들을 하나의 Address 객체로 병합
        Address address = new Address(
                request.getZipcode(),
                request.getStreetAddress(),
                request.getDetailAddress()
        );

        Application application = Application.builder()
                .user(user)
                .userName(request.getUserName())
                .birthDate(request.getBirthDate())
                .phoneNumber(request.getPhoneNumber())
                .applicationNumber(generateApplicationNumber())
                .farmName(request.getFarmName())
                .farmAddress(address)
                .businessRegistrationNumber(request.getBusinessRegistrationNumber())
                .mainProduct(request.getMainProduct())
                .annualSales(request.getAnnualSales())
                .onlineDistributionExperience(request.getOnlineDistributionExperience())
                .productCategory(request.getProductCategory())
                .shippingDate(request.getShippingDate())
                .fundingDesiredDate(request.getFundingDesiredDate())
                .productName(request.getProductName())
                .productSize(request.getProductSize())
                .sellingPrice(request.getSellingPrice())
                .availableQuantity(request.getAvailableQuantity())
                .fundingPlan(request.getFundingPlan())
                .status(ApplicationStatus.SUBMITTED)
                .build();

        Application submittedApplication = applicationRepository.save(application);
        return CreateApplicationResDto.from(submittedApplication);
    }

    // 사업 신청 기간 검증
    private void validateApplicationPeriod() {
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(APPLICATION_START_TIME) || now.isAfter(APPLICATION_END_TIME)) {
            throw new CustomException(ErrorCode.INVALID_APPLICATION_PERIOD);
        }
    }

    // 생년월일 검증
    private void validateBirthDate(String birthDateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(ResolverStyle.STRICT);
            LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);
            if (birthDate.isAfter(LocalDate.now())) throw new CustomException(ErrorCode.INVALID_BIRTH_DATE);
        } catch (DateTimeParseException e) {
            throw new CustomException(ErrorCode.INVALID_BIRTH_DATE);
        }
    }

    // 지원서 번호 자동 생성 메서드
    // 지원서 번호 규칙 : 접수 날짜 + 접수 순서 {YYYYMMDD}{0000~9999}
    private String generateApplicationNumber() {
        // 접수 날짜를 YYYYMMDD 형태의 문자열로 생성
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return applicationRepository.findTopByApplicationNumberStartingWithOrderByApplicationNumberDesc(date)
                // 당일 접수된 지원이 존재하면 map 블록 실행
                .map(application -> {
                    // 가장 최근에 발급된 지원서 번호
                    String lastApplication = application.getApplicationNumber();
                    // 앞의 8자리를 제거하여 순서인 뒷자리 4개만 추출하여 정수로 변환
                    // 202605150012 -> 0012 -> 12
                    int lastNumber = Integer.parseInt(lastApplication.substring(8));
                    // 추출한 순번에 1을 더해 4자리 문자열로 포맷팅
                    // 접수날짜 + 순번 문자열
                    return date + String.format("%04d", lastNumber + 1);
                })
                // 당일 접수된 지원이 없다면 접수날짜에 0000을 붙여 반환
                .orElse(date + "0000");
    }

    public ApplicationResDto getMyApplication(Long userId) {
        Application application = applicationRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.APPLICATION_NOT_FOUND));

        return ApplicationResDto.from(application);
    }

    public ApplicationStatusResDto getMyApplicationStatus(Long userId) {
        Application application = applicationRepository.findByUserId(userId).orElse(null);

        if(application == null) {
            return null;
        }

        return ApplicationStatusResDto.from(application);
    }

    @Transactional
    public void deleteMyApplication(Long userId) {
        Application application = applicationRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.APPLICATION_NOT_FOUND));

        // 취소 가능 시간 검증
        validateCancelPeriod();

        application.cancel();
    }

    private void validateCancelPeriod() {
        LocalDateTime now = LocalDateTime.now();

        // 신청 기간 내 + 마감 1시간 전까지 취소 가능
        if(now.isBefore(APPLICATION_START_TIME) || now.isAfter(APPLICATION_END_TIME.minusHours(1))) {
            throw new CustomException(ErrorCode.INVALID_CANCEL_PERIOD);
        }
    }
}
