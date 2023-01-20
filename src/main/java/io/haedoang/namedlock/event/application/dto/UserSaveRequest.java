package io.haedoang.namedlock.event.application.dto;

import io.haedoang.namedlock.event.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class UserSaveRequest {
    private String nickname;

    public static UserSaveRequest valueOf(String nickname) {
        return new UserSaveRequest(nickname);
    }

    public User toEntity() {
        return User.valueOf(nickname);
    }
}
