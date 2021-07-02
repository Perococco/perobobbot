package perobobbot.twitch.eventsub.api.deser;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.JsonModuleProvider;
import perobobbot.twitch.eventsub.api.*;
import perobobbot.twitch.eventsub.api.event.ContributionType;
import perobobbot.twitch.eventsub.api.event.OutcomeColor;
import perobobbot.twitch.eventsub.api.event.PredicationStatus;
import perobobbot.twitch.eventsub.api.event.StreamType;

import java.util.List;
import java.util.stream.Stream;

public class EventSubModule extends SimpleModule {


    public static @NonNull JsonModuleProvider provider() {
        return () -> List.of(new GuavaModule(), new JavaTimeModule(), new EventSubModule());
    }

    public EventSubModule() {
        this.setNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        Stream.of(SubscriptionStatus.class,
                  SubscriptionType.class,
                  StreamType.class,
                  Tier.class,
                  RewardRedemptionStatus.class,
                  PredicationStatus.class,
                  ContributionType.class,
                  OutcomeColor.class,
                  SubscriptionStatus.class,
                  PollStatus.class,
                  CriteriaType.class,
                  TransportMethod.class)
              .forEach(this::addIdentifiedEnumToModule);

        this.addDeserializer(Pagination.class, new PaginationDeserializer());
        this.addDeserializer(EventSubNotification.class, new NotificationDeserializer());

        this.setDeserializerModifier(new NotificationDeserModifier());
    }

    private <T extends IdentifiedEnum> void addIdentifiedEnumToModule(Class<T> type) {
        this.addSerializer(type, IdentifiedEnumTools.createSerializer());
        this.addDeserializer(type, IdentifiedEnumTools.createDeserializer(type));
    }

}
