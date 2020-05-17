package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perobobbot.data.com.UnknownUser;
import perobobbot.data.domain.User;

import java.util.Optional;

/**
 * @author Perococco
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(@NonNull String login);

    default User getByLogin(@NonNull String email) {
        return findByLogin(email).orElseThrow(() -> new UnknownUser(email));
    }

    default boolean doesUserExist(@NonNull String login) {
        return findByLogin(login).isPresent();
    }

}
