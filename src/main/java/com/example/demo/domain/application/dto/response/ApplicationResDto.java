package com.example.demo.domain.application.dto.response;

import com.example.demo.domain.application.entity.Address;
import com.example.demo.domain.application.entity.Application;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class ApplicationResDto {
    private final Long userId;
    private final String name;
    private final String gender; // 성별
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate birthDate;

    private final String phoneNumber;
    private final String businessName;

    // 카카오 주소 정보
    private final String zipcode;
    private final String streetAddress;
    private final String detailAddress;

    private final String businessRegistrationNumber;
    private final String agriRegistrationNumber; // 농업경영체번호
    private final String mainProduct;
    private final BigDecimal annualSales;
    private final Boolean onlineDistributionExperience;
    private final Boolean fundingExperience; // 펀딩 참여 경험
    private final String productCategory;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate shippingDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate fundingDesiredDate;
    private final String productName;
    private final String productSize;
    private final BigDecimal sellingPrice;
    private final Integer availableQuantity;
    private final String motivation; // 지원동기
    private final String fundingPlan;
    private final LocalDateTime createdAt;
    private final String storeLink;

    public static ApplicationResDto from(Application application) {
        Address address = application.getFarmAddress();

        return ApplicationResDto.builder()
                .userId(application.getUser().getId())
                .name(application.getUserName())
                .birthDate(application.getBirthDate())
                .gender(application.getGender())
                .phoneNumber(application.getPhoneNumber())
                .businessName(application.getBusinessName())
                .zipcode(address != null ? address.getZipcode() : null)
                .streetAddress(address != null ? address.getStreetAddress() : null)
                .detailAddress(address != null ? address.getDetailAddress() : null)
                .businessRegistrationNumber(application.getBusinessRegistrationNumber())
                .agriRegistrationNumber(application.getAgriRegistrationNumber())
                .mainProduct(application.getMainProduct())
                .annualSales(application.getAnnualSales())
                .onlineDistributionExperience(application.getOnlineDistributionExperience())
                .fundingExperience(application.getFundingExperience())
                .productCategory(application.getProductCategory())
                .shippingDate(application.getShippingDate())
                .fundingDesiredDate(application.getFundingDesiredDate())
                .productName(application.getProductName())
                .productSize(application.getProductSize())
                .sellingPrice(application.getSellingPrice())
                .availableQuantity(application.getAvailableQuantity())
                .motivation(application.getMotivation())
                .fundingPlan(application.getFundingPlan())
                .createdAt(application.getCreatedAt())
                .storeLink(application.getStoreLink())
                .build();
    }
}