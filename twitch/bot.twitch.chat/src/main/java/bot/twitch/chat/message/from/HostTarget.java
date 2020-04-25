package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.common.lang.CastTool;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.IRCCommand;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Getter
public abstract class HostTarget extends KnownMessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    private final int numberOfViewers;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.HOSTTARGET;
    }

    /**
     * @author perococco
     **/
    @Getter
    public static class Start extends HostTarget {

        @NonNull
        private final Channel hostingChannel;

        public Start(
                @NonNull IRCParsing ircParsing,
                int numberOfViewers,
                @NonNull Channel hostingChannel) {
            super(ircParsing, numberOfViewers);
            this.hostingChannel = hostingChannel;
        }

        @Override
        public void accept(@NonNull MessageFromTwitchVisitor visitor) {
            visitor.visit(this);
        }

    }

    /**
     * @author perococco
     **/
    @Getter
    public static class Stop extends HostTarget {

        public Stop(@NonNull IRCParsing ircParsing, int numberOfViewers) {
            super(ircParsing, numberOfViewers);
        }

        @Override
        public void accept(@NonNull MessageFromTwitchVisitor visitor) {
            visitor.visit(this);
        }
    }


    public static @NonNull HostTarget build(@NonNull AnswerBuilderHelper helper) {
        final String[] parameters = helper.lastParameter().split(" ");
        final int nbViewers = parameters.length<2? -1: CastTool.castToInt(parameters[1], -1);
        if (parameters[0].equals("-")) {
            return new HostTarget.Stop(helper.ircParsing(), nbViewers);
        }
        return new HostTarget.Start(helper.ircParsing(), nbViewers, Channel.create(parameters[0]));
    }
}
