package perobobbot.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.lang.DiscordBot;
import perobobbot.lang.PlatfomId;
import perobobbot.lang.Platform;

import javax.persistence.*;

@Entity
@DiscriminatorValue(PlatfomId.DISCORD)
@NoArgsConstructor
public class DiscordBotEntity extends PlatformBotEntity {

    @ManyToOne
    @JoinColumn(name = "DISCORD_USER_ID")
    @Getter
    private @NonNull DiscordUserEntity discordUser;

    DiscordBotEntity(@NonNull BotEntity bot, @NonNull DiscordUserEntity discordUser) {
        super(bot, Platform.DISCORD);
        this.discordUser = discordUser;
    }

    public @NonNull DiscordBot toView() {
        return new DiscordBot(getUuid(), this.getBot().toView(), this.discordUser.toView());
    }

    @Override
    public @NonNull PlatformUserEntity<?> getPlatformUser() {
        return discordUser;
    }
}
