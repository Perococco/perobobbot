package perococco.common.command;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.Policy;
import perobobbot.common.command.Command;
import perobobbot.common.command.CommandBundle;
import perobobbot.common.command.CommandFactory;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Executor;
import perobobbot.lang.fp.Consumer1;

import java.util.HashMap;
import java.util.Map;

public class PeroCommandFactory implements CommandFactory {

    private final Builder root = new Builder(null, "");

    @Override
    public @NonNull CommandFactory add(@NonNull String name, @NonNull Policy policy, @NonNull Consumer1<? super ExecutionContext> action) {
        String[] tokens = name.trim().split(" +");
        Builder current = root;
        for (String token : tokens) {
            current = current.getSub(token);
        }
        current.fallback = policy.createAccessPoint(action);
        return this;
    }

    @Override
    public @NonNull CommandFactory add(@NonNull String name, @NonNull Policy policy, @NonNull Runnable action) {
        return add(name,policy,Consumer1.toConsumer1(ctx -> action.run()));
    }

    @Override
    public @NonNull CommandFactory add(@NonNull String name, @NonNull Policy policy, @NonNull Executor<? super ExecutionContext> action) {
        final Consumer1<ExecutionContext> consumer = action::execute;
        return add(name, policy, consumer);
    }

    @Override
    public @NonNull CommandBundle build() {
        final CommandBundle commandBundle = CommandBundle.with(root.builders.values()
                                                                            .stream()
                                                                            .map(Builder::buildCommand)
                                                                            .collect(ImmutableList.toImmutableList()));
        return commandBundle;
    }

    @RequiredArgsConstructor
    private static class Builder {

        private final Builder parent;

        private final String name;

        private Consumer1<? super ExecutionContext> fallback = null;

        private final Map<String, Builder> builders = new HashMap<>();

        public Builder getSub(String token) {
            return builders.computeIfAbsent(token, n -> new Builder(this, n));
        }

        private String fullCommand() {
            if (parent == null) {
                return name;
            }
            return parent.fullCommand() + " " + name;
        }

        public @NonNull Command buildCommand() {
            final var fallback = this.fallback;
            if (builders.isEmpty() && fallback == null) {
                throw new IllegalStateException("The command '" + fullCommand() + "' does not have fallback nor sub command");
            }
            if (builders.isEmpty()) {
                return new SimpleCommand(name, fallback);
            }
            final Command[] subCommands = builders.values()
                                                  .stream()
                                                  .map(Builder::buildCommand)
                                                  .toArray(Command[]::new);

            if (fallback == null) {
                return ComplexCommand.create(name, subCommands);
            } else {
                return ComplexCommand.create(name, fallback, subCommands);
            }
        }
    }
}
