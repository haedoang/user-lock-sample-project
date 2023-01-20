package io.haedoang.namedlock.event.infra;

import io.haedoang.namedlock.event.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("회원 리파지토리 테스트")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 객체 생성하기")
    public void create() {
        //given
        User user = User.valueOf("해도앙");

        //when
        userRepository.save(user);

        //then
        assertThat(user.getId()).isNotNull();
    }

    @Test
    @DisplayName("닉네임으로 회원을 조회할 수 있다")
    public void findByNickname() {
        //given
        userRepository.save(User.valueOf("해도앙"));

        //when
        Optional<User> actual = userRepository.findByNickname("해도앙");

        //then
        assertThat(actual.isPresent()).isTrue();
    }
}