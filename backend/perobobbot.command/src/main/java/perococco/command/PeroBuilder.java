package perococco.command;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.Policy;
import perobobbot.command.Command;
import perobobbot.command.CommandBundle;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Executor;
import perobobbot.lang.fp.Consumer1;

import java.util.HashMap;
import java.util.Map;

public class PeroBuilder implements CommandBundle.Builder {

    private final BuilderNode root = new BuilderNode(null, "");

    @Override
    public @NonNull CommandBundle.Builder add(@NonNull String name, @NonNull Policy policy, @NonNull Consumer1<? super ExecutionContext> action) {
        String[] tokens = name.trim().split(" +");
        BuilderNode current = root;
        for (String token : tokens) {
            current = current.getSub(token);
        }
        current.fallback = policy.createAccessPoint(action);
        return this;
    }

    @Override
    public @NonNull CommandBundle.Builder add(@NonNull String name, @NonNull Policy policy, @NonNull Runnable action) {
        return add(name,policy,Consumer1.toConsumer1(ctx -> action.run()));
    }

    @Override
    public @NonNull CommandBundle.Builder add(@NonNull String name, @NonNull Policy policy, @NonNull Executor<? super ExecutionContext> action) {
        final Consumer1<ExecutionContext> consumer = action::execute;
        return add(name, policy, consumer);
    }

    @Override
    public @NonNull CommandBundle build() {
        return CommandBundle.with(root.builders.values()
                                               .stream()
                                               .map(BuilderNode::buildCommand)
                                               .collect(ImmutableList.toImmutableList()));
    }

    @RequiredArgsConstructor
    private static class BuilderNode {

        private final BuilderNode parent;

        private final String name;

        private Consumer1<? super ExecutionContext> fallback = null;

        private final Map<String, BuilderNode> builders = new HashMap<>();

        public BuilderNode getSub(String token) {
            return builders.computeIfAbsent(token, n -> new BuilderNode(this, n));
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
                                                  .map(BuilderNode::buildCommand)
                                                  .toArray(Command[]::new);

            if (fallback == null) {
                return ComplexCommand.create(name, subCommands);
            } else {
                return ComplexCommand.create(name, fallback, subCommands);
            }
        }
    }
}
