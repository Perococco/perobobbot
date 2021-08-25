package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.*;
import perobobbot.data.domain.UserEntity;
import perobobbot.data.domain.base.UserIdentification;
import perobobbot.data.jpa.repository.UserDetailProjection;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.jpa.repository.UserTokenRepository;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.UserService;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.PasswordEncoder;
import perobobbot.security.com.*;
import perobobbot.security.core.UserProvider;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * @author perococco
 */
@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPAUserService implements UserService, UserProvider {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final UserTokenRepository credentialRepository;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    @Override
    public User getUser(@NonNull String login) {
        return userRepository.getByLogin(login).toView();
    }

    @Override
    public @NonNull Optional<User> findUser(@NonNull String login) {
        return userRepository.findByLogin(login).map(UserEntity::toView);
    }

    @Override
    public boolean doesUserExist(@NonNull String login) {
        return userRepository.doesUserExist(login);
    }

    @Override
    public @NonNull User getUserDetails(@NonNull String login) {
        final var userInfo = userRepository.getUserDetail(login);
        if (userInfo.isEmpty()) {
            throw new UnknownUser(login);
        }
        final var builder = User.builder();
        final var first = userInfo.get(0);
        builder.login(first.getLogin())
               .identification(first.identification())
               .deactivated(first.isDeactivated())
               .locale(Locale.forLanguageTag(first.getLocale()))
               .jwtClaim(first.getJwtClaim());

        userInfo.stream()
                .map(UserDetailProjection::getRoleKind)
                .filter(Objects::nonNull)
                .map(IdentifiedEnumTools.mapper(RoleKind.class))
                .distinct()
                .forEach(builder::role);

        userInfo.stream()
                .map(UserDetailProjection::getOperation)
                .filter(Objects::nonNull)
                .map(IdentifiedEnumTools.mapper(Operation.class))
                .distinct()
                .forEach(builder::operation);

        return builder.build();
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

    @Override
    @Transactional
    public void changePassword(@NonNull String login, @NonNull String newPassword) {
        final var encodedPassword = passwordEncoder.encode(newPassword);

        final var user = userRepository.getByLogin(login);
        user.getIdentification().changePassword(encodedPassword);
        user.regenerateJwtClaim();

        userRepository.save(user);
    }
}
