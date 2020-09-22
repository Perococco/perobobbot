package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.*;

@RequiredArgsConstructor
public class PerococcoProgramBuilder<S> implements ProgramBuilder<S> {

    /**
     * the state of the program, used
     */
    @NonNull
    private final S state;

    private String name = null;

    private BackgroundTask backgroundTask = BackgroundTask.NOP;

    private final ImmutableMap.Builder<String,Instruction> instructionBuilder = ImmutableMap.builder();

    private MessageHandler messageHandler = e -> e;

    @Override
    public @NonNull ProgramBuilder<S> name(@NonNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NonNull ProgramBuilder<S> addBackgroundExecution(BackgroundTask.@NonNull Factory<? super S> factory) {
        backgroundTask = factory.create(state);
        return this;
    }

    @Override
    public @NonNull ProgramBuilder<S> addInstruction(@NonNull Instruction.Factory<? super S> factory) {
        final Instruction instruction = factory.create(state);
        this.instructionBuilder.put(instruction.getName(), instruction);
        return this;
    }

    @Override
    @NonNull
    public ProgramBuilder<S> setMessageHandler(MessageHandler.@NonNull Factory<? super S> factory) {
        messageHandler = factory.create(state);
        return this;
    }

    @Override
    public @NonNull Program build() {
        final BackgroundTask effective;
        if (BackgroundTask.NOP != this.backgroundTask) {
            effective = new BackgroundTask() {
                @Override
                public void start() {
                    System.out.println("Start background task for "+name);
                    backgroundTask.start();
                }

                @Override
                public void stop() {
                    System.out.println("Stop background task for "+name);
                    backgroundTask.stop();
                }
            };
        } else {
            effective = backgroundTask;
        }
        return new PerococcoProgram(name, instructionBuilder.build(), effective, messageHandler);
    }
}
