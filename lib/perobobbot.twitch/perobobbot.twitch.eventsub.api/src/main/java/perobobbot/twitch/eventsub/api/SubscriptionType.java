package perobobbot.twitch.eventsub.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.twitch.eventsub.api.event.*;
import perobobbot.twitch.eventsub.api.subscription.*;

import java.util.Arrays;

@RequiredArgsConstructor
public enum SubscriptionType implements IdentifiedEnum {
    CHANNEL_UPDATE("channel.update", "1", ChannelUpdateEvent.class, ChannelUpdate.FACTORY),
    CHANNEL_FOLLOW("channel.follow", "1", FollowEvent.class, ChannelFollow.FACTORY),
    CHANNEL_SUBSCRIBE("channel.subscribe", "1", SubscribeEvent.class, ChannelSubscribe.FACTORY),
    CHANNEL_SUBSCRIPTION_END("channel.subscription.end", "1", SubscriptionEndEvent.class, ChannelSubscriptionEnd.FACTORY),
    CHANNEL_SUBSCRIPTION_GIFT("channel.subscription.gift","1", SubscriptionGiftEvent.class, ChannelSubscriptionGift.FACTORY),
    CHANNEL_SUBSCRIPTION_MESSAGE("channel.subscription.message","beta", SubscriptionMessageEvent.class, ChannelSubscriptionMessage.FACTORY),
    CHANNEL_CHEER("channel.cheer","1", CheerEvent.class, ChannelCheer.FACTORY),
    CHANNEL_RAID("channel.raid","1", RaidEvent.class, ChannelRaid.FACTORY),
    CHANNEL_BAN("channel.ban", "1", BanEvent.class, ChannelBan.FACTORY),
    CHANNEL_UNBAN("channel.unban","1", UnbanEvent.class, ChannelUnban.FACTORY),
    CHANNEL_MODERATOR_ADD("channel.moderator.add","1", ModeratorAddEvent.class, ChannelModeratorAdd.FACTORY),
    CHANNEL_MODERATOR_REMOVE("channel.moderator.remove","1", ModeratorRemoveEvent.class, ChannelModeratorRemove.FACTORY),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_ADD("channel.channel_points_custom_reward.add", "1", ChannelPointsCustomRewardAddEvent.class, ChannelPointsCustomRewardAdd.FACTORY),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_UPDATE("channel.channel_points_custom_reward.update","1", ChannelPointsCustomRewardUpdateEvent.class, ChannelPointsCustomRewardUpdate.FACTORY),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REMOVE("channel.channel_points_custom_reward.remove", "1", ChannelPointsCustomRewardRemoveEvent.class, ChannelPointsCustomRewardRemove.FACTORY),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_ADD("channel.channel_points_custom_reward_redemption.add", "1", ChannelPointsCustomRewardRedemptionAddEvent.class, ChannelPointsCustomRewardRedemptionAdd.FACTORY),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_UPDATE("channel.channel_points_custom_reward_redemption.update", "1", ChannelPointsCustomRewardRedemptionUpdateEvent.class, ChannelPointsCustomRewardRedemptionUpdate.FACTORY),
    CHANNEL_POLL_BEGIN("channel.poll.begin","1", PollBeginEvent.class, ChannelPollBegin.FACTORY),
    CHANNEL_POLL_PROGRESS("channel.poll.progress","1", PollProgressEvent.class, ChannelPollProgress.FACTORY),
    CHANNEL_POLL_END("channel.poll.end","1", PollEndEvent.class, ChannelPollEnd.FACTORY),
    CHANNEL_PREDICTION_BEGIN("channel.prediction.begin","1", PredictionBeginEvent.class, ChannelPredictionBegin.FACTORY),
    CHANNEL_PREDICTION_PROGRESS("channel.prediction.progress","1", PredictionProgressEvent.class, ChannelPredictionProgress.FACTORY),
    CHANNEL_PREDICTION_LOCK("channel.prediction.lock","1", PredictionLockEvent.class, ChannelPredictionLock.FACTORY),
    CHANNEL_PREDICTION_END("channel.prediction.end","1", PredictionEndEvent.class, ChannelPredictionEnd.FACTORY),
    EXTENSION_BITS_TRANSACTION_CREATE("extension.bits_transaction.create", "1", ExtensionBitsTransactionCreateEvent.class, ExtensionBitsTransactionCreate.FACTORY),
    CHANNEL_HYPE_TRAIN_BEGIN("channel.hype_train.begin", "1", HypeTrainBeginEvent.class, HypeTrainBegin.FACTORY),
    CHANNEL_HYPE_TRAIN_PROGRESS("channel.hype_train.progress","1", HypeTrainProgressEvent.class, HypeTrainProgress.FACTORY),
    CHANNEL_HYPE_TRAIN_END("channel.hype_train.end","1", HypeTrainEndEvent.class, HypeTrainEnd.FACTORY),
    STREAM_ONLINE("stream.online","1", StreamOnlineEvent.class, StreamOnline.FACTORY),
    STREAM_OFFLINE("stream.offline","1", StreamOfflineEvent.class, StreamOffline.FACTORY),
    USER_AUTHORIZATION_REVOKE("user.authorization.revoke","1", UserAuthorizationRevokeEvent.class, UserAuthorizationRevoke.FACTORY),
    USER_UPDATE("user.update","1", UserUpdateEvent.class, UserUpdate.FACTORY),
    ;

    @Getter
    private final @NonNull String identification;

    @Getter
    private final @NonNull String version;

    @Getter
    private final @NonNull Class<? extends EventSubEvent> eventType;

    private final @NonNull SubscriptionFactory subscriptionFactory;

    public @NonNull Subscription create(@NonNull ImmutableMap<String, String> condition) {
        return subscriptionFactory.create(condition);
    }

    public static @NonNull ImmutableSet<String> getIdentifications() {
        return Holder.VALUE_IDENTIFICATIONS;
    }

    private static class Holder {

        private static final @NonNull ImmutableSet<String> VALUE_IDENTIFICATIONS;

        static {
            VALUE_IDENTIFICATIONS = Arrays.stream(values())
                                  .map(SubscriptionType::getIdentification)
                                  .collect(ImmutableSet.toImmutableSet());
        }
    }
}
