package perobobbot.twitch.eventsub.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.twitch.eventsub.api.event.*;

@RequiredArgsConstructor
public enum SubscriptionType implements IdentifiedEnum {
    CHANNEL_UPDATE("channel.update", "1", ChannelUpdateEvent.class),
    CHANNEL_FOLLOW("channel.follow", "1", FollowEvent.class),
    CHANNEL_SUBSCRIBE("channel.subscribe", "1", SubscribeEvent.class),
    CHANNEL_SUBSCRIPTION_END("channel.subscription.end", "1", SubscriptionEndEvent.class),
    CHANNEL_SUBSCRIPTION_GIFT("channel.subscription.gift","1", SubscriptionGiftEvent.class),
    CHANNEL_SUBSCRIPTION_MESSAGE("channel.subscription.message","beta", SubscriptionMessageEvent.class),
    CHANNEL_CHEER("channel.cheer","1", CheerEvent.class),
    CHANNEL_RAID("channel.raid","1", RaidEvent.class),
    CHANNEL_BAN("channel.ban", "1", BanEvent.class),
    CHANNEL_UNBAN("channel.unban","1", UnbanEvent.class),
    CHANNEL_MODERATOR_ADD("channel.moderator.add","1", ModeratorAddEvent.class),
    CHANNEL_MODERATOR_REMOVE("channel.moderator.remove","1", ModeratorRemoveEvent.class),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_ADD("channel.channel_points_custom_reward.add","1", ChannelPointsCustomRewardAddEvent.class),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_UPDATE("channel.channel_points_custom_reward.update","1", ChannelPointsCustomRewardUpdateEvent.class),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REMOVE("channel.channel_points_custom_reward.remove","1", ChannelPointsCustomRewardRemoveEvent.class),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_ADD("channel.channel_points_custom_reward_redemption.add","1", ChannelPointsCustomRewardRedemptionAddEvent.class),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_UPDATE("channel.channel_points_custom_reward_redemption.update","1", ChannelPointsCustomRewardRedemptionUpdateEvent.class),
    CHANNEL_POLL_BEGIN("channel.poll.begin","1", PollBeginEvent.class),
    CHANNEL_POLL_PROGRESS("channel.poll.progress","1", PollProgressEvent.class),
    CHANNEL_POLL_END("channel.poll.end","1", PollEndEvent.class),
    CHANNEL_PREDICTION_BEGIN("channel.prediction.begin","1", PredictionBeginEvent.class),
    CHANNEL_PREDICTION_PROGRESS("channel.prediction.progress","1", PredictionProgressEvent.class),
    CHANNEL_PREDICTION_LOCK("channel.prediction.lock","1", PredictionLockEvent.class),
    CHANNEL_PREDICTION_END("channel.prediction.end","1", PredictionEndEvent.class),
    EXTENSION_BITS_TRANSACTION_CREATE("extension.bits_transaction.create","1", ExtensionBitsTransactionCreateEvent.class),
    CHANNEL_HYPE_TRAIN_BEGIN("channel.hype_train.begin", "1", HypeTrainBeginEvent.class),
    CHANNEL_HYPE_TRAIN_PROGRESS("channel.hype_train.progress","1", HypeTrainProgressEvent.class),
    CHANNEL_HYPE_TRAIN_END("channel.hype_train.end","1", HypeTrainEndEvent.class),
    STREAM_ONLINE("stream.online","1", StreamOnlineEvent.class),
    STREAM_OFFLINE("stream.offline","1", StreamOfflineEvent.class),
    USER_AUTHORIZATION_REVOKE("user.authorization.revoke","1", UserAuthorizationRevokeEvent.class),
    USER_UPDATE("user.update","1", UserUpdateEvent.class),
    ;

    @Getter
    private final @NonNull String identification;

    @Getter
    private final @NonNull String version;

    @Getter
    private final @NonNull Class<? extends EventSubEvent> eventType;
}
