package perobobbot.connect4;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.connect4.Connect4Extension;
import perobobbot.connect4.action.LaunchGame;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;

import java.time.Duration;

public class Connect4ExtensionFactory extends ExtensionFactoryBase<Connect4Extension> {


    public Connect4ExtensionFactory() {
        super(Connect4Extension.NAME);
    }

    @Override
    protected @NonNull Connect4Extension createExtension(@NonNull Parameters parameters) {
        return new Connect4Extension(parameters.getOverlay());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(
            @NonNull Connect4Extension extension,
            @NonNull Parameters parameters,
            @NonNull CommandDefinition.Factory factory) {
        final var accessRule = AccessRule.create(Role.TRUSTED_USER, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("c4 [%s] [%s]".formatted(LaunchGame.PLAYER1,LaunchGame.PLAYER2), accessRule, new LaunchGame(extension,parameters.getChatController(),false)),
                factory.create("c4 start-big [%s] [%s]".formatted(LaunchGame.PLAYER1,LaunchGame.PLAYER2), accessRule, new LaunchGame(extension,parameters.getChatController(),true)),
                factory.create("c4 stop", accessRule, extension::stop)
        );
    }

}
