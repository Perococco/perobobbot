package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.fp.Function2;

public sealed interface UserIdentity permits DiscordIdentity, TwitchIdentity {

    @NonNull String getUserId();
    @NonNull String getLogin();
    @NonNull Platform getPlatform();

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);

    interface Visitor<T> {
        @NonNull T visit(@NonNull TwitchIdentity twitchIdentity);
        @NonNull T visit(@NonNull DiscordIdentity discordIdentity);
    }

    static <T> @NonNull Visitor<T> visitorWith(
            @NonNull Function1<? super TwitchIdentity, ? extends T> twitchAction,
            @NonNull Function1<? super DiscordIdentity, ? extends T> discordAction
            ) {
        return new Visitor<T>() {
            @Override
            public @NonNull T visit(@NonNull TwitchIdentity twitchIdentity) {
                return twitchAction.f(twitchIdentity);
            }

            @Override
            public @NonNull T visit(@NonNull DiscordIdentity discordIdentity) {
                return discordAction.f(discordIdentity);
            }
        };
    }

    static <P,T> @NonNull Visitor<T> visitorWith(
            @NonNull P parameter,
            @NonNull Function2<? super P, ? super TwitchIdentity, ? extends T> twitchAction,
            @NonNull Function2<? super P, ? super DiscordIdentity, ? extends T> discordAction
            ) {
        return visitorWith(twitchAction.f1(parameter),discordAction.f1(parameter));
    }

}
