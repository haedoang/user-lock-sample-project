package io.haedoang.namedlock.user.infra;

import io.haedoang.namedlock.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT GET_LOCK(?, 1)", nativeQuery = true)
    Integer getLock(String keyword);

    @Query(value = "SELECT IS_FREE_LOCK(?)", nativeQuery = true)
    Integer isFreeLock(String keyword);

    @Query(value = "SELECT RELEASE_LOCK(?)", nativeQuery = true)
    Integer releaseLock(String keyword);

    Optional<User> findByNickname(String nickname);
}
