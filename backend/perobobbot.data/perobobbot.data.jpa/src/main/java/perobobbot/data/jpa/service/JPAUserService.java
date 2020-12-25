package perobobbot.data.jpa.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.DuplicateUser;
import perobobbot.data.com.UserDTO;
import perobobbot.data.domain.JwtTokenGenerator;
import perobobbot.data.domain.User;
import perobobbot.data.domain.transformers.Transformers;
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
    private final JwtTokenGenerator jwtTokenGenerator;

    @NonNull
    @Override
    public UserDTO getUserInfo(@NonNull String login) {
        return Transformers.toDTO(userRepository.getByLogin(login));
    }

    @NonNull
    @Override
    @Transactional
    public UserDTO createUser(@NonNull CreateUserParameters parameters) {
        checkNotDuplicate(parameters.getLogin());

        final User user = User.create(parameters.withPasswordEncoded(passwordEncoder::encode));

        return Transformers.toDTO(userRepository.save(user));
    }

    @NonNull
    @Override
    public String getJWTToken(@NonNull String login) {
        final User user = userRepository.getByLogin(login);
        return jwtTokenGenerator.createTokenFromUser(user);
    }


    private void checkNotDuplicate(@NonNull String email) {
        if (userRepository.doesUserExist(email)) {
            throw new DuplicateUser(email);
        }
    }


}
