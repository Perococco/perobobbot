package perobobbot.twitch.chat.message.from;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.irc.IRCParsing;
import perobobbot.common.irc.Prefix;
import perobobbot.lang.fp.Function1;
import perobobbot.twitch.chat.Capability;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.TagKey;

import java.util.Optional;
import java.util.function.Function;

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
        return IRCCommand.findFromString(ircParsing.getCommand());
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
        return mapper.apply(ircParsing.getParams().parameterAt(parameterIndex));
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
        return ircParsing.tagValue(key.getKeyName());
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
        return prefix().getNickOrServerName();
    }

    @NonNull
    private Prefix prefix() {
        return ircParsing.getPrefix()
                         .orElseThrow(() -> buildException("No prefix defined"));
    }

    @NonNull
    private IllegalArgumentException buildException(@NonNull String message) {
        return new IllegalArgumentException(message+". Raw Message:'"+ircParsing.getRawMessage()+"'");
    }

    @NonNull
    public String parameterAt(int parameterIndex) {
        return ircParsing.getParams().parameterAt(parameterIndex);
    }
}
