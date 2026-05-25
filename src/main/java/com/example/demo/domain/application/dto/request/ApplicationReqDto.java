package com.example.demo.domain.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class ApplicationReqDto {
    @NotBlank(message = "이름은 필수입니다.")
    private final String userName;

    @NotNull(message = "생년월일은 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private final LocalDate birthDate;

    @NotBlank(message = "전화번호는 필수입니다.")
    private final String phoneNumber;

    @NotBlank(message = "농장명은 필수입니다.")
    private final String farmName;

    private final String affiliatedNhName;

    @NotBlank(message = "우편번호는 필수입니다.")
    private final String zipcode;

    @NotBlank(message = "주소는 필수입니다.")
    private final String streetAddress;

    @NotBlank(message = "상세 주소는 필수입니다.")
    private final String detailAddress;

    @NotBlank(message = "사업자 등록 번호는 필수입니다.")
    private final String businessRegistrationNumber;

    @NotBlank(message = "주요 품목은 필수입니다.")
    private final String mainProduct;

    @NotNull(message = "연매출액은 필수입니다.")
    private final BigDecimal annualSales;

    @NotNull(message = "온라인 유통경험 여부는 필수입니다.")
    private final Boolean onlineDistributionExperience;

    @NotBlank(message = "품목은 필수입니다.")
    private final String productCategory;

    @NotNull(message = "출하시기는 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate shippingDate;

    @NotNull(message = "펀딩 희망시기는 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate fundingDesiredDate;

    @NotBlank(message = "상품명은 필수입니다.")
    private final String productName;

    @NotBlank(message = "규격은 필수입니다.")
    private final String productSize;

    @NotNull(message = "판매 가격은 필수입니다.")
    private final BigDecimal sellingPrice;

    @NotNull(message = "판매 가능 수량은 필수입니다.")
    private final Integer availableQuantity;

    @NotBlank(message = "펀딩 계획은 필수입니다.")
    @Size(max = 1000, message = "펀딩 계획은 1000자 이내여야 합니다.")
    private final String fundingPlan;

    @NotNull(message = "동의 여부는 필수입니다.")
    @AssertTrue(message = "개인정보 수집 및 이용에 동의해야 지원이 가능합니다.")
    private final Boolean agreement;
}
