package perobobbot.server.config.security;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import perobobbot.data.domain.Operation;
import perobobbot.data.domain.Role;
import perobobbot.data.domain.User;
import perobobbot.lang.ListTool;

import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtractorOfGrantedAuthorities {

    public static ImmutableList<GrantedAuthority> extract(@NonNull User user) {
        return new ExtractorOfGrantedAuthorities(user).extract();
    }

    @NonNull
    private final User user;

    private ImmutableList<GrantedAuthority> grantedAuthorities;

    @NonNull
    private ImmutableList<GrantedAuthority> extract() {
        this.createGrantedAuthorities();
        return grantedAuthorities;
    }

    private void createGrantedAuthorities() {
        this.grantedAuthorities = Stream.concat(
                roleNameStream(),
                allowedOperationNameStream()
        )
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(ListTool.collector());
    }

    private Stream<String> roleNameStream() {
        return user.roleStream()
                   .map(Role::getName)
                   .map(r -> "ROLE_" + r);
    }

    private Stream<String> allowedOperationNameStream() {
        return user.roleStream()
                   .flatMap(Role::allowedOperationStream)
                   .map(Operation::getName)
                   .map(op -> "OP_" + op);
    }


}
