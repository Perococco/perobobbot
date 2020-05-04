package perococco.bot.twitch.program;


import bot.common.lang.*;
import bot.common.lang.fp.Consumer1;
import bot.twitch.chat.Channel;
import bot.twitch.chat.PrivMsgFromTwitchListener;
import bot.twitch.chat.TwitchChatIO;
import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import bot.twitch.program.ChatProgram;
import bot.twitch.program.ChatProgramManager;
import bot.twitch.program.ProgramCommand;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PerococcoChatProgramManager implements ChatProgramManager {

    @NonNull
    private final TwitchChatIO twitchChatIO;

    @NonNull
    private final String prefixOfCommandForManager;

    @NonNull
    private final String prefixOfCommandForProgram;

    @NonNull
    private ImmutableMap<String, ChatProgram> programs = ImmutableMap.of();

    @NonNull
    private ImmutableSet<String> namesOfEnabledPrograms = ImmutableSet.of();

    private Subscription subscription = Subscription.NONE;

    @Synchronized
    public void start() {
        subscription.unsubscribe();
        subscription = twitchChatIO.addPrivateMessageListener(new Listener());
    }

    @Synchronized
    public void stop() {
        subscription.unsubscribe();
        subscription = Subscription.NONE;
    }

    @Override
    public @NonNull ChatProgramManager registerChatProgram(@NonNull ChatProgram chatProgram) {
        this.programs = MapTool.add(this.programs, chatProgram.name(), chatProgram);
        return this;
    }

    private class Listener implements PrivMsgFromTwitchListener {

        @Override
        public void onPrivateMessage(@NonNull ReceivedMessage<PrivMsgFromTwitch> reception) {
            {
                if (handle(prefixOfCommandForManager, this::handleCommandForManager, reception)) {
                    return;
                }
                handle(prefixOfCommandForProgram, this::handleCommandForProgram, reception);
            }
        }

        private boolean handle(@NonNull String prefix, @NonNull Consumer<? super ProgramCommand> consumer, @NonNull ReceivedMessage<PrivMsgFromTwitch> reception) {
            final Optional<ProgramCommand> command = CommandExtractor.extract(prefix, reception.message().payload())
                                                                     .map(c -> c.toProgramCommandWithReception(reception));
            command.ifPresent(consumer);
            return command.isPresent();
        }


        private void handleCommandForProgram(@NonNull ProgramCommand command) {
            for (ChatProgram program : programs.values()) {
                if (namesOfEnabledPrograms.contains(program.name())) {
                    if (program.handleCommand(twitchChatIO, command)) {
                        return;
                    }
                }
            }
        }

        private final ImmutableMap<String, Consumer1<ProgramCommand>> managerCommands = ImmutableMap.<String, Consumer1<ProgramCommand>>builder()
                .put("list", this::listPrograms)
                .put("start", this::startProgram)
                .put("stop", this::stopProgram)
                .put("startAll", this::startAllPrograms)
                .put("stopAll", this::stopAllPrograms)
                .put("help", this::help)
                .build();

        private void handleCommandForManager(@NonNull ProgramCommand programCommand) {
            final Consumer1<ProgramCommand> action = managerCommands.getOrDefault(programCommand.commandName(), this::unkownCommand);
            action.accept(programCommand);
        }

        private void listPrograms(@NonNull ProgramCommand programCommand) {
            twitchChatIO.message(programCommand.channel(), "programs: " + String.join(", ", programs.keySet()));
        }

        private void startAllPrograms(@NonNull ProgramCommand programCommand) {
            namesOfEnabledPrograms = programs.keySet();
            twitchChatIO.message(programCommand.channel(),"All programs have been started");
        }

        private void stopAllPrograms(@NonNull ProgramCommand programCommand) {
            namesOfEnabledPrograms = ImmutableSet.of();
            twitchChatIO.message(programCommand.channel(),"All programs have been stopped");
        }

        private void help(@NonNull ProgramCommand programCommand) {
            final Channel channel = programCommand.channel();
            if (programCommand.parameters().isEmpty()) {
                final String listOfCommands = managerCommands.keySet()
                                                             .stream().map(c -> prefixOfCommandForManager + c)
                                                             .collect(Collectors.joining(", "));
                twitchChatIO.message(channel, "Manager commands: " + listOfCommands);
            } else {
                final String requestedProgramName = programCommand.getFirstParameter();
                final ChatProgram chatProgram = programs.get(requestedProgramName);
                if (chatProgram == null) {
                    this.sendInvalidProgramName(channel, requestedProgramName);
                } else {
                    final String listOfCommands = chatProgram.commands()
                                                             .stream()
                                                             .map(c -> prefixOfCommandForProgram + c)
                                                             .collect(Collectors.joining(", "));
                    twitchChatIO.message(channel, "'"+requestedProgramName+"' commands:"+listOfCommands);
                }
            }


        }

        private void startProgram(@NonNull ProgramCommand programCommand) {
            final Channel channel = programCommand.channel();
            try {
                final String programName = programCommand.parameters().get(0);
                if (programs.containsKey(programName)) {
                    if (namesOfEnabledPrograms.contains(programName)) {
                        twitchChatIO.message(channel, "program '" + programName + "' is started already");
                    } else {
                        namesOfEnabledPrograms = SetTool.add(namesOfEnabledPrograms, programName);
                        twitchChatIO.message(channel, "program '" + programName + "' started");
                    }
                } else {
                    this.sendInvalidProgramName(channel, programName);
                }
            } catch (Throwable e) {
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                twitchChatIO.message(channel, "Error while execution @start command. Syntax is @start <program name>");
            }
        }

        private void sendInvalidProgramName(@NonNull Channel channel, @NonNull String programName) {
            twitchChatIO.message(channel, "program '" + programName + "' is not a valid name of a program (use @list to get the list of valid programs)");
        }

        private void stopProgram(ProgramCommand programCommand) {
            final Channel channel = programCommand.channel();
            try {
                final String programName = programCommand.parameters().get(0);
                if (programs.containsKey(programName)) {
                    if (namesOfEnabledPrograms.contains(programName)) {
                        namesOfEnabledPrograms = SetTool.remove(namesOfEnabledPrograms, programName);
                        twitchChatIO.message(channel, "program '" + programName + "' stopped");
                    } else {
                        twitchChatIO.message(channel, "program '" + programName + "' is stopped already");
                    }
                } else {
                    this.sendInvalidProgramName(channel, programName);
                }
            } catch (Throwable e) {
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                twitchChatIO.message(channel, "Error while execution @stop command. Syntax is @stop <program name>");
            }

        }

        private void unkownCommand(ProgramCommand programCommand) {
            twitchChatIO.message(programCommand.channel(), "the command '" + programCommand.commandName() + "' is unknown");
        }
    }


}
