package io.haedoang.namedlock.user.application;

import io.haedoang.namedlock.user.application.dto.UserSaveRequest;
import io.haedoang.namedlock.user.domain.User;
import io.haedoang.namedlock.user.exception.DebounceException;
import io.haedoang.namedlock.user.exception.DuplicateException;
import io.haedoang.namedlock.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private static final int IS_FREE_LOCK = 1;

    private static final int LOCK_SUCCESS = 1;

    @Transactional
    public Long save(UserSaveRequest request) {
        if (!isFreeLock(request.getNickname())) {
            throw new DebounceException();
        }

        if (!getLock(request.getNickname())) {
            throw new DebounceException();
        }

        duplicateCheck(request.getNickname());
        User user = userRepository.save(request.toEntity());
        releaseLock(request.getNickname());
        return user.getId();
    }

    private void duplicateCheck(String nickname) {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new DuplicateException();
        }
    }

    @Transactional
    public boolean getLock(String keyword) {
        Integer lockResult = userRepository.getLock(keyword);
        log.info("getlock: {}", lockResult);
        return lockResult == LOCK_SUCCESS;
    }

    @Transactional
    public Integer releaseLock(String keyword) {
        Integer lockResult = userRepository.releaseLock(keyword);
        log.info("releaseLock: {}", lockResult);
        return lockResult;
    }

    public boolean isFreeLock(String keyword) {
        Integer lockResult = userRepository.isFreeLock(keyword);
        log.info("isFreeLock: {}", lockResult);
        return lockResult == IS_FREE_LOCK;
    }
}
