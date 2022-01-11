package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.lang.PlatfomId;
import perobobbot.lang.Platform;
import perobobbot.lang.TwitchIdentity;
import perobobbot.lang.TwitchUser;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@DiscriminatorValue(PlatfomId.TWITCH)
public final class TwitchUserEntity extends PlatformUserEntity<TwitchIdentity> {

    @Column(name = "TWITCH_LOGIN")
    @NotBlank
    private String login = "";

    @Column(name = "TWITCH_PSEUDO")
    @NotBlank
    private String pseudo = "";

    public TwitchUserEntity(@NonNull TwitchIdentity twitchIdentity) {
        super(twitchIdentity.getUserId(), Platform.TWITCH);
        this.login = twitchIdentity.getLogin();
        this.pseudo = twitchIdentity.getPseudo();
    }

    @Override
    public @NonNull TwitchUser toView() {
        return TwitchUser.builder()
                         .id(getUuid())
                         .userId(getUserId())
                         .login(login)
                         .pseudo(pseudo)
                         .build();
    }

    @Override
    public void update(@NonNull TwitchIdentity userIdentity) {
        this.checkSamePlatformAndIdThan(userIdentity);
        this.login = userIdentity.getLogin();
        this.pseudo = userIdentity.getPseudo();
    }
}
