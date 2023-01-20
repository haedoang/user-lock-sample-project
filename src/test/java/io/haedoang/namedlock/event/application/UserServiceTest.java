package io.haedoang.namedlock.event.application;

import io.haedoang.namedlock.event.application.dto.UserSaveRequest;
import io.haedoang.namedlock.event.domain.User;
import io.haedoang.namedlock.event.exception.DuplicateException;
import io.haedoang.namedlock.event.infra.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("유저 서비스 테스트")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("중복된 닉네임이 없는 경우 등록 가능")
    public void create() {
        //given
        UserSaveRequest given = UserSaveRequest.valueOf("해도앙");
        when(userRepository.isFreeLock(anyString())).thenReturn(1);
        when(userRepository.getLock(anyString())).thenReturn(1);
        when(userRepository.findByNickname(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(user.getId()).thenReturn(1L);


        //when
        Long actual = userService.save(given);

        //then
        assertThat(actual).isEqualTo(1L);
    }


    @Test
    @DisplayName("중복된 닉네임인 경우 예외")
    public void createFailByDuplicatedNickname() {
        //given
        UserSaveRequest given = UserSaveRequest.valueOf("해도앙");
        when(userRepository.isFreeLock(anyString())).thenReturn(1);
        when(userRepository.getLock(anyString())).thenReturn(1);
        when(userRepository.findByNickname(anyString())).thenReturn(Optional.of(given.toEntity()));

        //when & then
        assertThatThrownBy(() -> userService.save(given))
                .isInstanceOf(DuplicateException.class);
    }
}