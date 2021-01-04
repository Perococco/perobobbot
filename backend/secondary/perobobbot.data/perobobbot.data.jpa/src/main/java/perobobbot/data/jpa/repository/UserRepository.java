package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perobobbot.data.com.UnknownUser;
import perobobbot.data.domain.UserEntity;

import java.util.Optional;

/**
 * @author Perococco
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(@NonNull String login);

    default UserEntity getByLogin(@NonNull String login) {
        return findByLogin(login).orElseThrow(() -> new UnknownUser(login));
    }

    default boolean doesUserExist(@NonNull String login) {
        return findByLogin(login).isPresent();
    }

}
