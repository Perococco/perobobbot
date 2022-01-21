package perobobbot.discord.gateway.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Intents {
    GUILDS(1),
    GUILD_MEMBERS(1 << 1),
    GUILD_BANS(1 << 2),
    GUILD_EMOJIS_AND_STICKERS(1 << 3),
    GUILD_INTEGRATIONS(1 << 4),
    GUILD_WEBHOOKS(1 << 5),
    GUILD_INVITES(1 << 6),
    GUILD_VOICE_STATES(1 << 7),
    GUILD_PRESENCES(1 << 8),
    GUILD_MESSAGES(1 << 9),
    GUILD_MESSAGE_REACTIONS(1 << 10),
    GUILD_MESSAGE_TYPING(1 << 11),
    DIRECT_MESSAGES(1 << 12),
    DIRECT_MESSAGE_REACTIONS(1 << 13),
    DIRECT_MESSAGE_TYPING(1 << 14),
    GUILD_SCHEDULED_EVENTS(1 << 16),
    ;

    private final int mask;

    public static int computeMask(@NonNull Intents... intents) {
        return Arrays.stream(intents)
                     .map(i -> i.mask)
                     .reduce(0,(i1, i2) -> i1 | i2);
    }
}
