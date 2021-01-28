package perobobbot.connect4.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.connect4.Connect4Extension;
import perobobbot.connect4.action.MoveProposal;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perobobbot.lang.Role;

import java.time.Duration;

@Component
public class Connect4ExtensionFactory extends ExtensionFactoryBase<Connect4Extension> {

    public static @NonNull Plugin provider() {
        return Plugin.with(PluginType.EXTENSION, "Connect 4", Connect4ExtensionFactory.class);
    }

    public Connect4ExtensionFactory(@NonNull Parameters parameters) {
        super(Connect4Extension.NAME, parameters);
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
        final var moveProposalRule = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(10));
        return ImmutableList.of(
                factory.create("c4 start", accessRule, extension::start),
                factory.create("c4 stop", accessRule, extension::stop),
                factory.create("c4 play {column}", moveProposalRule, new MoveProposal(extension))
        );
    }

}