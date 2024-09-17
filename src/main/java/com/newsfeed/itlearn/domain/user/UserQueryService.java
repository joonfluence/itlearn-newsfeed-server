package com.newsfeed.itlearn.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {
    private final UserEntityRepository userEntityRepository;

    public List<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }
    public UserEntity findById(Long id) {
        return userEntityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
