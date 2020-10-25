package perococco.perobobbot.program.greeter;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.CollectionTool;
import perobobbot.common.lang.User;
import perobobbot.common.lang.fp.Function1;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultGreetingMessageCreator implements GreetingMessageCreator {

    @Override
    public @NonNull ImmutableSet<String> create(@NonNull ChannelInfo channelInfo, @NonNull ImmutableSet<User> greeters) {
        final ImmutableSet.Builder<String> messageBuilder = ImmutableSet.builder();

        final Set<User> toProcess = new HashSet<>(greeters);

        {
            //special messages
            for (User greeter : greeters) {
                formSpecialMessage(channelInfo, greeter)
                        .ifPresent(msg -> {
                            messageBuilder.add(msg);
                            toProcess.remove(greeter);
                        });
            }
        }

        if (!toProcess.isEmpty()) {
            toProcess.stream()
                     .collect(CollectionTool.split(10))
                     .stream()
                     .map(this::formGenericMessage)
                     .forEach(messageBuilder::add);
        }

        return messageBuilder.build();
    }

    @NonNull
    public String formGenericMessage(@NonNull ImmutableCollection<User> users) {
        return users.stream()
                    .map(User::getHighlightedUserName)
                    .collect(Collectors.joining(", ", "Hello ", " !"));
    }

    @NonNull
    public Optional<String> formSpecialMessage(@NonNull ChannelInfo channelInfo, @NonNull User user) {

        final String userId = user.getUserId();
        if (channelInfo.isOwnedBy(user)) {
            return Optional.of("Salut Chef !!!");
        }
        return Optional.ofNullable(Holder.SPECIAL_MESSAGES.get(userId))
                       .map(f -> f.f(user));
    }

    private static class Holder {

        private static final ImmutableMap<String, Function1<? super User, String>> SPECIAL_MESSAGES =
                ImmutableMap.<String, Function1<? super User, String>>builder()
                        .put("ghostcatfr", u -> "Bonjour " + u.getHighlightedUserName() + " ! Est-ce que tu as passé une bonne journée ?")
                        .build();
    }


}
