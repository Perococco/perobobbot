package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.DuplicateUser;
import perobobbot.data.com.UpdateUserParameters;
import perobobbot.security.com.User;
import perobobbot.data.domain.UserEntity;
import perobobbot.data.jpa.repository.CredentialRepository;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.service.UnsecuredService;
import perobobbot.security.core.UserProvider;
import perobobbot.data.service.UserService;
import perobobbot.lang.PasswordEncoder;

import java.util.Locale;

/**
 * @author Perococco
 */
@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPAUserService implements UserService, UserProvider {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final CredentialRepository credentialRepository;

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
        final UserEntity user = UserEntity.create(parameters.withPasswordEncoded(passwordEncoder));
        return userRepository.save(user).toView();
    }

    @Override
    @Transactional
    public @NonNull User updateUser(@NonNull String login, @NonNull UpdateUserParameters parameters) {
        final var user = userRepository.getByLogin(login);
        parameters.getLanguageTag().map(Locale::forLanguageTag).ifPresent(user::setLocale);
        return userRepository.save(user).toView();
    }

    @Override
    public @NonNull ImmutableList<User> listAllUser() {
        return userRepository.findAll()
                             .stream()
                             .map(UserEntity::toView)
                             .collect(ImmutableList.toImmutableList());
    }

    private void checkNotDuplicate(@NonNull String email) {
        if (userRepository.doesUserExist(email)) {
            throw new DuplicateUser(email);
        }
    }


}
