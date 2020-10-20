package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.fp.Function1;
import perobobbot.common.lang.fp.Value2;
import perobobbot.common.lang.fp.Value3;
import perobobbot.program.core.*;
import perobobbot.service.core.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class PerococcoProgramBuilder<P> implements ProgramBuilder<P> {

    private final @NonNull Function1<? super Services, ? extends P> parameterFactory;

    private String name;

    private Services services;

    private BackgroundTask.@NonNull Factory<P> backgroundTaskFactory = (services, programState) -> BackgroundTask.NOP;

    private final Map<String, Value2<Execution.Factory<? super P>, ExecutionPolicy>> chatCommandFactories = new HashMap<>();

    private MessageHandler.Factory<P> messageHandler = p -> e -> false;

    @Override
    public @NonNull Program build() {
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(services, "Services must not be null");
        Objects.requireNonNull(parameterFactory, "parameterFactory must not be null");

        final P parameter = parameterFactory.f(services);
        final ImmutableMap<String, ChatCommandWithPolicyHandling> chatCommands = chatCommandFactories.entrySet()
                                                                                                     .stream()
                                                                                                     .map(Value2::of)
                                                                                                     .map(Value3::flatten)
                                                                                                     .map(v -> v.mapB(d -> d.create(parameter)))
                                                                                                     .map(v -> v.use(ChatCommandWithPolicyHandling::new))
                                                                                                     .collect(MapTool.collector(ChatCommandWithPolicyHandling::getName));

        return new PerococcoProgram(
                name,
                backgroundTaskFactory.create(services, parameter),
                chatCommands,
                messageHandler.create(parameter)
        );
    }


    @Override
    public @NonNull ProgramBuilder<P> setName(@NonNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NonNull ProgramBuilder<P> setServices(@NonNull Services services) {
        this.services = services;
        return this;
    }

    @Override
    public @NonNull ProgramBuilder<P> setBackgroundTask(BackgroundTask.@NonNull Factory<P> factory) {
        this.backgroundTaskFactory = factory;
        return this;
    }


    @Override
    public @NonNull ProgramBuilder<P> attachChatCommand(@NonNull String commandName,
                                                        @NonNull Execution.@NonNull Factory<P> factory,
                                                        @NonNull ExecutionPolicy policy) {
        this.chatCommandFactories.put(commandName, Value2.of(factory,policy));
        return this;
    }

    @Override
    public @NonNull ProgramBuilder<P> setMessageHandler(MessageHandler.@NonNull Factory<P> factory) {
        this.messageHandler = factory;
        return this;
    }
}
