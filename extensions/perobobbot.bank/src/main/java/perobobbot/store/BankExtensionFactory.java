package perobobbot.store;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Bank;
import perobobbot.lang.Role;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;
import perobobbot.store.BankExtension;
import perobobbot.store.action.ReadBalance;
import perobobbot.store.action.WriteToBalance;

import java.time.Duration;

@Component
public class BankExtensionFactory extends ExtensionFactoryBase<BankExtension> {

    public BankExtensionFactory() {
        super(BankExtension.NAME);
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(
                Requirement.required(IO.class),
                Requirement.required(Bank.class)
        );
    }

    @Override
    protected @NonNull BankExtension createExtension(@NonNull ServiceProvider serviceProvider) {
        return new BankExtension(serviceProvider.getService(IO.class),
                                 serviceProvider.getService(Bank.class));
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull BankExtension extension, @NonNull ServiceProvider serviceProvider, CommandDefinition.@NonNull Factory factory) {
        final var readBalance = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(30), Role.ADMINISTRATOR.cooldown(Duration.ZERO));
        final var writeBalance = AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("points",readBalance, new ReadBalance(extension)),
                factory.create("points {%s} {%s}".formatted(WriteToBalance.USERID_PARAMETER, WriteToBalance.AMOUNT_PARAMETER), writeBalance, new WriteToBalance(extension))
        );
    }
}
