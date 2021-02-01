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
import perobobbot.store.BankExtension;
import perobobbot.store.action.ReadBalance;
import perobobbot.store.action.WriteToBalance;

import java.time.Duration;

@Component
public class BankExtensionFactory extends ExtensionFactoryBase<BankExtension> {

    public static Plugin provider() {
        return Plugin.with(PluginType.EXTENSION, BankExtension.NAME, BankExtensionFactory.class);
    }


    public BankExtensionFactory(@NonNull Parameters parameters) {
        super(BankExtension.NAME, parameters);
    }

    @Override
    protected @NonNull BankExtension createExtension(@NonNull Parameters parameters) {
        return new BankExtension(parameters.getIo(), parameters.getBank());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull BankExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var readBalance = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(30), Role.ADMINISTRATOR.cooldown(Duration.ZERO));
        final var writeBalance = AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("points",readBalance, new ReadBalance(extension)),
                factory.create("addpoints {%s} {%s}".formatted(WriteToBalance.USERID_PARAMETER, WriteToBalance.AMOUNT_PARAMETER), writeBalance, new WriteToBalance(extension))
        );
    }
}
