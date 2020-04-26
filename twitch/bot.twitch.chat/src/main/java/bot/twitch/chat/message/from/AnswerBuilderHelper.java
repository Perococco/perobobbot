package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.common.irc.Prefix;
import bot.common.lang.fp.Function1;
import bot.twitch.chat.Capability;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.message.TagKey;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class AnswerBuilderHelper {

    @NonNull
    @Getter
    private final IRCParsing ircParsing;

    @NonNull
    public Optional<IRCCommand> command() {
        return IRCCommand.findFromString(ircParsing.command());
    }

    @NonNull
    public ImmutableSet<Capability> capabilities() {
        return ircParsing.splitLastParameter(" ")
                         .map(Capability::find)
                         .flatMap(Optional::stream)
                         .collect(ImmutableSet.toImmutableSet());
    }

    @NonNull
    public String lastParameter() {
        return ircParsing.lastParameter();
    }


    @NonNull
    public <T> T mapParameter(int parameterIndex, @NonNull Function<? super String, ? extends T> mapper) {
        return mapper.apply(ircParsing.params().parameterAt(parameterIndex));
    }

    @NonNull
    public Channel channelFromParameterAt(int parameterIndex) {
        return mapParameter(parameterIndex,Channel::create);
    }

    @NonNull
    public <T> T tagValue(@NonNull TagKey key, @NonNull Function1<? super String, ? extends T> mapper) {
        return mapper.f(tagValue(key));
    }

    @NonNull
    public String tagValue(@NonNull TagKey key) {
        return optionalTagValue(key)
                .orElseThrow(() -> buildException("Could not find tag with name '"+key.name()+"'"));
    }

    @NonNull
    public Optional<String> optionalTagValue(@NonNull TagKey key) {
        return ircParsing.tagValue(key.keyName());
    }

    @NonNull
    public String tagValue(@NonNull TagKey key, @NonNull String defaultValue) {
        return optionalTagValue(key)
                .orElse(defaultValue);
    }

    @NonNull
    public <T> Optional<T> optionalTagValue(@NonNull TagKey key, @NonNull Function<? super String, ? extends T> mapper) {
        return optionalTagValue(key)
                .map(mapper);
    }

    public boolean tagValueAsBoolean(@NonNull TagKey key, boolean defaultValue) {
        final String value = optionalTagValue(key).orElse(null);
        return value == null?defaultValue:value.equals("1");
    }

    public int tagValueAsInt(@NonNull TagKey key, int defaultValue) {
        return optionalTagValueAsInt(key).orElse(defaultValue);
    }

    @NonNull
    public Optional<Integer> optionalTagValueAsInt(@NonNull TagKey key) {
        return optionalTagValue(key, Integer::parseInt);
    }


    @NonNull
    public String userFromPrefix() {
        return prefix().nickOrServerName();
    }

    @NonNull
    private Prefix prefix() {
        return ircParsing.prefix()
                         .orElseThrow(() -> buildException("No prefix defined"));
    }

    @NonNull
    private IllegalArgumentException buildException(@NonNull String message) {
        return new IllegalArgumentException(message+". Raw Message:'"+ircParsing.rawMessage()+"'");
    }

    @NonNull
    public String parameterAt(int parameterIndex) {
        return ircParsing.params().parameterAt(parameterIndex);
    }
}
