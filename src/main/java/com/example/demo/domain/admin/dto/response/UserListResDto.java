package com.example.demo.domain.admin.dto.response;

import com.example.demo.domain.user.entity.User;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Builder
@Getter
public class UserListResDto {

    private final Integer currentPage;
    private final Long totalCount;
    private final Integer totalPages;
    private final List<UserSummaryDto> users;

    public static UserListResDto from(Page<User> users){
        List<UserSummaryDto> userDtos = users.stream()
                                            .map(UserSummaryDto::from)
                                            .toList();

        return UserListResDto.builder()
                   .currentPage(users.getNumber() + 1)
                   .totalCount(users.getTotalElements())
                   .totalPages(users.getTotalPages())
                   .users(userDtos)
                   .build();
    }
}
