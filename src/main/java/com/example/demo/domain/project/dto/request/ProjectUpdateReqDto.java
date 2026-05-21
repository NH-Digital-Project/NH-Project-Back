package com.example.demo.domain.project.dto.request;

import com.example.demo.domain.project.entity.ProjectStatus;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Getter
@NoArgsConstructor // RequestBody 역직렬화를 위해 필요
public class ProjectUpdateReqDto {

    // 빈 문자열, 공백 입력을 차단하기 위해 @Pattern 추가
    @Pattern(regexp = ".*\\S.*", message = "농장명은 공백만으로 비워둘 수 없습니다.")
    private String farmName;

    @Pattern(regexp = ".*\\S.*", message = "품목은 공백만으로 비워둘 수 없습니다.")
    private String productCategory;

    private String thumbnailImageUrl;

    @Pattern(regexp = ".*\\S.*", message = "소개글은 공백만으로 비워둘 수 없습니다.")
    private String description;

    private ProjectStatus status;

    @URL(message = "올바른 URL 형식이 아닙니다.")
    @Pattern(regexp = ".*\\S.*", message = "해피빈 URL은 공백만으로 비워둘 수 없습니다.")
    private String happyBeanUrl;
}
