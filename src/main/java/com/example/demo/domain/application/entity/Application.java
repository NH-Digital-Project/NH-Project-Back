package com.example.demo.domain.application.entity;

import com.example.demo.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String birthDate;

    private String phoneNumber;

    private String applicationNumber;

    private String farmName;

    private String affiliatedNhName;

    private String farmAddress;

    private String businessRegistrationNumber;

    private String mainProduct;

    private Integer annualSales;

    private Boolean onlineDistributionExperience;

    private String productCategory;

    private String shippingDate;

    private String fundingDesiredDate;

    private String productName;

    private String productSize;

    private Integer sellingPrice;

    private Integer availableQuantity;

    @Column(columnDefinition = "TEXT")
    private String fundingPlan;

}

