package com.example.demo.domain.application.dto.response;

import com.example.demo.domain.application.entity.Address;
import com.example.demo.domain.application.entity.Application;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class ApplicationResDto {
    private final Long userId;
    private final String name;
    private final String birthDate;
    private final String phoneNumber;
    private final String farmName;
    private final String affiliatedNhName;

    // 카카오 주소 정보
    private final String zipcode;
    private final String streetAddress;
    private final String detailAddress;

    private final String businessRegistrationNumber;
    private final String mainProduct;
    private final Integer annualSales;
    private final Boolean onlineDistributionExperience;
    private final String productCategory;
    private final String shippingDate;
    private final String fundingDesiredDate;
    private final String productName;
    private final String productSize;
    private final Integer sellingPrice;
    private final Integer availableQuantity;
    private final String fundingPlan;
    private final LocalDateTime createdAt;

    public static ApplicationResDto from(Application application) {
        Address address = application.getFarmAddress();

        return ApplicationResDto.builder()
                .userId(application.getUser().getId())
                .name(application.getUserName())
                .birthDate(application.getBirthDate())
                .phoneNumber(application.getPhoneNumber())
                .farmName(application.getFarmName())
                .affiliatedNhName(application.getAffiliatedNhName())
                .zipcode(address != null ? address.getZipcode() : null)
                .streetAddress(address != null ? address.getStreetAddress() : null)
                .detailAddress(address != null ? address.getDetailAddress() : null)
                .businessRegistrationNumber(application.getBusinessRegistrationNumber())
                .mainProduct(application.getMainProduct())
                .annualSales(application.getAnnualSales())
                .onlineDistributionExperience(application.getOnlineDistributionExperience())
                .productCategory(application.getProductCategory())
                .shippingDate(application.getShippingDate())
                .fundingDesiredDate(application.getFundingDesiredDate())
                .productName(application.getProductName())
                .productSize(application.getProductSize())
                .sellingPrice(application.getSellingPrice())
                .availableQuantity(application.getAvailableQuantity())
                .fundingPlan(application.getFundingPlan())
                .createdAt(application.getCreatedAt())
                .build();
    }
}