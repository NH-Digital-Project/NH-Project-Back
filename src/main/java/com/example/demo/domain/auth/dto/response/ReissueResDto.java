package com.example.demo.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ReissueResDto(
    String accessToken,

    @JsonIgnore
    String refreshToken
) {

}
