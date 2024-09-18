package com.newsfeed.itlearn.domain.user;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String name;
    private String email;
    private String password;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
