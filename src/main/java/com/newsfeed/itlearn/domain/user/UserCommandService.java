package com.newsfeed.itlearn.domain.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService {
    private final UserEntityRepository userEntityRepository;

    public UserEntity createUser(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        userEntityRepository.deleteById(id);
    }
}
