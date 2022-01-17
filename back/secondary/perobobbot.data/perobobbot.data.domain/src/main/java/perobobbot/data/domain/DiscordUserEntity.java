package perobobbot.data.domain;

import lombok.*;
import perobobbot.lang.DiscordIdentity;
import perobobbot.lang.DiscordUser;
import perobobbot.lang.PlatfomId;
import perobobbot.lang.Platform;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED)
@DiscriminatorValue(PlatfomId.DISCORD)
public final class DiscordUserEntity extends PlatformUserEntity<DiscordIdentity> {

    @Column(name = "DISCORD_LOGIN")
    @NotBlank
    private @NonNull String login = "";

    @Column(name = "DISCORD_DISCRIMINATOR")
    @NotBlank
    private @NonNull String discriminator = "";

    public DiscordUserEntity(@NonNull DiscordIdentity discordIdentity) {
        super(discordIdentity.getUserId(), Platform.DISCORD);
        this.login = discordIdentity.getLogin();
        this.discriminator = discordIdentity.getDiscriminator();
    }

    @Override
    public @NonNull DiscordUser toView() {
        return DiscordUser.builder()
                .id(this.getUuid())
                .userId(getUserId())
                .login(login)
                .discriminator(discriminator)
                .build();
    }

    @Override
    public void update(@NonNull DiscordIdentity userIdentity) {
        this.checkSamePlatformAndIdThan(userIdentity);
        this.login = userIdentity.getLogin();
        this.discriminator = userIdentity.getDiscriminator();
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.DISCORD;
    }

    @Override
    DiscordBotEntity createPlatformBot(@NonNull BotEntity bot) {
        return new DiscordBotEntity(bot, this);
    }
}
