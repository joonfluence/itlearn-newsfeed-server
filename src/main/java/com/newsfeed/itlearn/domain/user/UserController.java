package com.newsfeed.itlearn.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @GetMapping
    public List<UserEntity> findUsers() {
        return userQueryService.findAll();
    }

    @GetMapping("/{id}")
    public UserEntity findUserById(@PathVariable Long id) {
        return userQueryService.findById(id);
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserCreateRequest request) {
        UserEntity entity = request.toEntity();
        return userCommandService.createUser(entity);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userCommandService.deleteUser(id);
    }
}
