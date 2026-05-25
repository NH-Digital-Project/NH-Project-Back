package com.example.demo.domain.application.entity;

import com.example.demo.domain.application.status.ApplicationStatus;
import com.example.demo.domain.user.entity.User;
import com.example.demo.global.common.entity.BaseSoftDeleteEntity;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "applications")
@Entity
@SQLRestriction("deleted_at IS NULL")
public class Application extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 취소 후 재지원을 위해 unique = true 조건 제거
    private User user;

    private String userName;

    private String birthDate;

    private String phoneNumber;

    private String gender; // 성별

    private String applicationNumber;

    private String farmName;

    @Embedded
    private Address farmAddress;

    private String businessRegistrationNumber;

    private String agriRegistrationNumber; // 농업경영체번호

    private String mainProduct;

    private Integer annualSales;

    private Boolean onlineDistributionExperience;

    private Boolean fundingExperience; // 펀딩 참여 경험

    private String productCategory;

    private String shippingDate;

    private String fundingDesiredDate;

    private String productName;

    private String productSize;

    private Integer sellingPrice;

    private Integer availableQuantity;

    @Column(columnDefinition = "TEXT")
    private String motivation; // 지원동기

    @Column(columnDefinition = "TEXT")
    private String fundingPlan;


    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Builder
    private Application(Long id, User user, String userName, String birthDate, String phoneNumber,
        String gender, String applicationNumber, String farmName, Address farmAddress,
        String businessRegistrationNumber, String agriRegistrationNumber, String mainProduct, Integer annualSales,
        Boolean onlineDistributionExperience, Boolean fundingExperience, String productCategory, String shippingDate,
        String fundingDesiredDate, String productName, String productSize, Integer sellingPrice,
        Integer availableQuantity, String motivation, String fundingPlan, ApplicationStatus status) {
        this.id = id;
        this.user = user;
        this.userName = userName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.applicationNumber = applicationNumber;
        this.farmName = farmName;
        this.farmAddress = farmAddress;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.agriRegistrationNumber = agriRegistrationNumber;
        this.mainProduct = mainProduct;
        this.annualSales = annualSales;
        this.onlineDistributionExperience = onlineDistributionExperience;
        this.fundingExperience = fundingExperience;
        this.productCategory = productCategory;
        this.shippingDate = shippingDate;
        this.fundingDesiredDate = fundingDesiredDate;
        this.productName = productName;
        this.productSize = productSize;
        this.sellingPrice = sellingPrice;
        this.availableQuantity = availableQuantity;
        this.motivation = motivation;
        this.fundingPlan = fundingPlan;
        this.status = status != null ? status : ApplicationStatus.SUBMITTED;
    }

    public void cancel() {
        // ApplicationStatus가 SUBMITTED일때만 취소 가능
        if (this.status != ApplicationStatus.SUBMITTED) {
            throw new CustomException(ErrorCode.APPLICATION_NOT_DELETABLE);
        }

        this.status = ApplicationStatus.CANCELED;
        this.delete();
    }

    // 선정업체 등록 시 상태를 APPROVED로 변경
    public void approve() {
        if (this.status != ApplicationStatus.SUBMITTED) {
            throw new CustomException(ErrorCode.INVALID_APPLICATION_STATUS);
        }
        this.status = ApplicationStatus.APPROVED;
    }

    // 선정업체 삭제 시 상태를 SUBMITTED로 변경
    public void submit() {
        this.status = ApplicationStatus.SUBMITTED;
    }
}

