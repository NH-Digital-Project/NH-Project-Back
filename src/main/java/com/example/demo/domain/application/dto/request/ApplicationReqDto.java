package com.example.demo.domain.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationReqDto {
    @NotBlank(message = "이름은 필수입니다.")
    private String userName;

    @NotBlank(message = "생년월일은 필수입니다.")
    private String birthDate;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phoneNumber;

    @NotBlank(message = "농장명은 필수입니다.")
    private String farmName;

    @NotBlank(message = "소속 농협명은 필수입니다.")
    private String affiliatedNhName;

    @NotBlank(message = "우편번호는 필수입니다.")
    private String zipcode;

    @NotBlank(message = "주소는 필수입니다.")
    private String streetAddress;

    @NotBlank(message = "상세 주소는 필수입니다.")
    private String detailAddress;

    @NotBlank(message = "사업자 등록 번호는 필수입니다.")
    private String businessRegistrationNumber;

    @NotBlank(message = "주요 품목은 필수입니다.")
    private String mainProduct;

    @NotNull(message = "연매출액은 필수입니다.")
    private Integer annualSales;

    @NotNull(message = "온라인 유통경험 여부는 필수입니다.")
    private Boolean onlineDistributionExperience;

    @NotBlank(message = "품목은 필수입니다.")
    private String productCategory;

    @NotBlank(message = "출하시기는 필수입니다.")
    private String shippingDate;

    @NotBlank(message = "펀딩 희망시기는 필수입니다.")
    private String fundingDesiredDate;

    @NotBlank(message = "상품명은 필수입니다.")
    private String productName;

    @NotBlank(message = "규격은 필수입니다.")
    private String productSize;

    @NotNull(message = "판매 가격은 필수입니다.")
    private Integer sellingPrice;

    @NotNull(message = "판매 가능 수량은 필수입니다.")
    private Integer availableQuantity;

    @NotBlank(message = "펀딩 계획은 필수입니다.")
    @Size(max = 1000, message = "펀딩 계획은 1000자 이내여야 합니다.")
    private String fundingPlan;

    @NotNull(message = "동의 여부는 필수입니다.")
    @AssertTrue(message = "개인정보 수집 및 이용에 동의해야 지원이 가능합니다.")
    private Boolean agreement;
}
