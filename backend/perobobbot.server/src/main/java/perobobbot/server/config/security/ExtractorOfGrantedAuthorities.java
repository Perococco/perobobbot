package perobobbot.server.config.security;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import perobobbot.data.com.User;
import perobobbot.data.com.Operation;
import perobobbot.data.com.RoleKind;
import perobobbot.lang.ListTool;

import java.util.Collection;
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
        return user.getRoles()
                   .stream()
                   .map(RoleKind::getGrantedAuthorityName);
    }

    private Stream<String> allowedOperationNameStream() {
        return user.getOperations()
                   .stream()
                   .map(Operation::getGrantedAuthorityName);
    }


}
