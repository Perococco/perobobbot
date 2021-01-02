package perobobbot.data.jpa.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.DuplicateUser;
import perobobbot.data.com.User;
import perobobbot.data.domain.UserEntity;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.service.UserService;

/**
 * @author Perococco
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPAUserService implements UserService {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    @Override
    public User getUser(@NonNull String login) {
        return userRepository.getByLogin(login).toView();
    }

    @NonNull
    @Override
    @Transactional
    public User createUser(@NonNull CreateUserParameters parameters) {
        checkNotDuplicate(parameters.getLogin());

        final UserEntity user = UserEntity.create(parameters.withPasswordEncoded(passwordEncoder::encode));

        return userRepository.save(user).toView();
    }

    private void checkNotDuplicate(@NonNull String email) {
        if (userRepository.doesUserExist(email)) {
            throw new DuplicateUser(email);
        }
    }


}
