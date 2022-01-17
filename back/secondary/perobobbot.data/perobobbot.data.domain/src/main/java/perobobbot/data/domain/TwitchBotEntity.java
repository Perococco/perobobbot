package perobobbot.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.lang.PlatfomId;
import perobobbot.lang.Platform;
import perobobbot.lang.TwitchBot;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@DiscriminatorValue(PlatfomId.TWITCH)
public class TwitchBotEntity extends PlatformBotEntity {


    @ManyToOne
    @JoinColumn(name = "TWITCH_USER_ID")
    @Getter
    private @NonNull TwitchUserEntity twitchUser;


    TwitchBotEntity(@NonNull BotEntity bot, @NonNull TwitchUserEntity twitchUser) {
        super(bot,Platform.TWITCH);
        this.twitchUser = twitchUser;
    }

    public @NonNull TwitchBot toView() {
        return new TwitchBot(getUuid(), this.getBot().toView(), this.twitchUser.toView());
    }

    @Override
    public @NonNull TwitchUserEntity getPlatformUser() {
        return twitchUser;
    }
}
