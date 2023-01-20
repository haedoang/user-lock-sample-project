package io.haedoang.namedlock.user.ui;

import io.haedoang.namedlock.user.application.UserService;
import io.haedoang.namedlock.user.application.dto.UserSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserSaveRequest userSaveRequest) {
        Long savedId = userService.save(userSaveRequest);
        return ResponseEntity.created(URI.create("api/v1/users/" + savedId)).build();
    }
}
