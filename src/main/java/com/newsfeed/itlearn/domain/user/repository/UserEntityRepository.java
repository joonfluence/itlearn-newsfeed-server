package com.newsfeed.itlearn.domain.user.repository;

import com.newsfeed.itlearn.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}