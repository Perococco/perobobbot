package perobobbot.greeter;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.ChatUser;
import perobobbot.lang.CollectionTool;
import perobobbot.lang.ChatUser;
import perobobbot.lang.fp.Function1;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultGreetingMessageCreator implements GreetingMessageCreator {

    @Override
    public @NonNull ImmutableSet<String> create(@NonNull ChannelInfo channelInfo, @NonNull ImmutableSet<ChatUser> greeters) {
        final ImmutableSet.Builder<String> messageBuilder = ImmutableSet.builder();

        final Set<ChatUser> toProcess = new HashSet<>(greeters);

        {
            //special messages
            for (ChatUser greeter : greeters) {
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
    public String formGenericMessage(@NonNull ImmutableCollection<ChatUser> users) {
        return users.stream()
                    .map(ChatUser::getHighlightedUserName)
                    .collect(Collectors.joining(", ", "Hello ", " !"));
    }

    @NonNull
    public Optional<String> formSpecialMessage(@NonNull ChannelInfo channelInfo, @NonNull ChatUser user) {

        final String userId = user.getUserId();
        if (channelInfo.isOwnedBy(user)) {
            return Optional.of("Salut Chef !!!");
        }
        return Optional.ofNullable(Holder.SPECIAL_MESSAGES.get(userId))
                       .map(f -> f.f(user));
    }

    private static class Holder {

        private static final ImmutableMap<String, Function1<? super ChatUser, String>> SPECIAL_MESSAGES =
                ImmutableMap.<String, Function1<? super ChatUser, String>>builder()
                        .put("ghostcatfr", u -> "Bonjour " + u.getHighlightedUserName() + " ! Est-ce que tu as passé une bonne journée ?")
                        .build();
    }


}
