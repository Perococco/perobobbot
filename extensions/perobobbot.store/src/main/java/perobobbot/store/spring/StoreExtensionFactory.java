package perobobbot.store.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perobobbot.lang.Role;
import perobobbot.store.StoreExtension;
import perobobbot.store.action.ReadBalance;
import perobobbot.store.action.WriteBalance;

import java.time.Duration;

@Component
public class StoreExtensionFactory extends ExtensionFactoryBase<StoreExtension> {

    public static Plugin provider() {
        return Plugin.with(PluginType.EXTENSION, StoreExtension.NAME, StoreExtensionFactory.class);
    }


    public StoreExtensionFactory(@NonNull Parameters parameters) {
        super(StoreExtension.NAME, parameters);
    }

    @Override
    protected @NonNull StoreExtension createExtension(@NonNull Parameters parameters) {
        return new StoreExtension(parameters.getIo(),parameters.getStoreController());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull StoreExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var readBalance = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(30), Role.ADMINISTRATOR.cooldown(Duration.ZERO));
        final var writeBalance = AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("points",readBalance, new ReadBalance(extension)),
                factory.create("setpoints {%s} {%s}".formatted(WriteBalance.USERID_PARAMETER,WriteBalance.AMOUNT_PARAMETER),writeBalance, new WriteBalance(extension))
        );
    }
}
