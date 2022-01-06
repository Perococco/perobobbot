package perobobbot.discord.oauth.api;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Scope;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public enum DiscordScope implements Scope {
    ACTIVITIES_READ("activities.read","allows your app to fetch data from a user's 'Now Playing/Recently Played' list",true),
    ACTIVITIES_WRITE("activities.write","allows your app to update a user's activity - requires Discord approval (NOT REQUIRED FOR GAMESDK ACTIVITY MANAGER)"),
    APPLICATIONS_BUILDS_READ("applications.builds.read","allows your app to read build data for a user's applications"),
    APPLICATIONS_BUILDS_UPLOAD("applications.builds.upload","allows your app to upload/update builds for a user's applications",true),
    APPLICATIONS_COMMANDS("applications.commands","allows your app to use commands in a guild"),
    APPLICATIONS_COMMANDS_UPDATE("applications.commands.update","allows your app to update its commands via this bearer token - client credentials grant only"),
    APPLICATIONS_ENTITLEMENTS("applications.entitlements","allows your app to read entitlements for a user's applications"),
    APPLICATIONS_STORE_UPDATE("applications.store_update","allows your app to read and update store data (SKUs, store listings, achievements, etc.) for a user's applications"),
    BOT("bot","for oauth2 bots, this puts the bot in the user's selected guild by default"),
    CONNECTIONS("connections","allows /users/@me/connections to return linked third-party accounts"),
    EMAIL("email","enables /users/@me to return an email"),
    GDM_JOIN("gdm.join","allows your app to join users to a group dm"),
    GUILDS("guilds","allows /users/@me/guilds to return basic information about all of a user's guilds"),
    GUILDS_JOIN("guilds.join","allows /guilds/{guild.id}/members/{user.id} to be used for joining users to a guild"),
    GUILDS_MEMBERS_READ("guilds.members.read","allows /users/@me/guilds/{guild.id}/member to return a user's member information in a guild"),
    IDENTIFY("identify","allows /users/@me without email"),
    MESSAGES_READ("messages.read","for local rpc server api access, this allows you to read messages from all client channels (otherwise restricted to channels/guilds your app creates)"),
    RELATIONSHIPS_READ("relationships.read","allows your app to know a user's friends and implicit relationships",true),
    RPC("rpc","for local rpc server access, this allows you to control a user's local Discord client",true),
    RPC_ACTIVITIES_WRITE("rpc.activities_write","for local rpc server access, this allows you to update a user's activity",true),
    RPC_NOTIFICATIONS_READ("rpc.notifications_read","for local rpc server access, this allows you to receive notifications pushed out to the user",true),
    RPC_VOICE_READ("rpc._voice_read","for local rpc server access, this allows you to read a user's voice settings and listen for voice events",true),
    RPC_VOICE_WRITE("rpc._voice_write","for local rpc server access, this allows you to update a user's voice settings",true),
    WEBHOOK_INCOMING("webhook.incoming","this generates a webhook that is returned in the oauth token response for authorization code grants"),

    ;

    @Getter
    private final @NonNull String name;
    @Getter
    private final @NonNull String description;
    @Getter
    private final boolean discordApprovalRequired;

    DiscordScope(@NonNull String name,@NonNull String description)
    {
        this(name,description,false);
    }

    public static @NonNull Optional<DiscordScope> findScopeByName(@NonNull String name) {
        return Optional.ofNullable(Holder.SCOPE_BY_NAMES.get(name));
    }


    private static class Holder {
        private static final ImmutableMap<String,DiscordScope> SCOPE_BY_NAMES;

        static {
            SCOPE_BY_NAMES = Arrays.stream(DiscordScope.values())
                    .collect(ImmutableMap.toImmutableMap(DiscordScope::getName, v->v));
        }
    }

}
