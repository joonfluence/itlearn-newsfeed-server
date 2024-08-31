package com.newsfeed.itlearn.domain.test;

import com.newsfeed.itlearn.global.redis.service.RedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @DisplayName("Redis에 값 저장한 값이 불러온 값과 일치해야 한다.")
    @Test
    void shouldReturnSameValueAsSavedInRedis() {
        // Given
        String sampleValue = "value";
        redisService.saveValue("key", sampleValue);
        // When
        Object redisServiceValue = redisService.getValue("key");
        // Then
        assertEquals(sampleValue, redisServiceValue);
    }

    @DisplayName("Redis에 값 저장한 값이 불러온 값이 없는 경우, 예외를 반환해야 한다.")
    @Test
    void shouldThrowExceptionAsNotSavedInRedis() {
        // Given
        String sampleValue = "value";
        String sampleKey = "key";
        String errorKey = "key2";

        // When
        redisService.saveValue(sampleKey, sampleValue);

        // Then
        assertThrows(RuntimeException.class, () -> {
                if (redisService.getValue(errorKey) == null) {
                    throw new RuntimeException("Key not found");
                }
            }
        );
    }
}