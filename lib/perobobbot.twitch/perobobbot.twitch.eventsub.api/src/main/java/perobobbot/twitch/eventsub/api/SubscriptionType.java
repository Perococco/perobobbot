package perobobbot.twitch.eventsub.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum SubscriptionType implements IdentifiedEnum {
    CHANNEL_UPDATE("channel.update","1"),
    CHANNEL_FOLLOW("channel.follow","1"),
    CHANNEL_SUBSCRIBE("channel.subscribe","1"),
    CHANNEL_SUBSCRIPTION_END("channel.subscription.end","1"),
    CHANNEL_SUBSCRIPTION_GIFT("channel.subscription.gift","1"),
    CHANNEL_SUBSCRIPTION_MESSAGE("channel.subscription.message","beta"),
    CHANNEL_CHEER("channel.cheer","1"),
    CHANNEL_RAID("channel.raid","1"),
    CHANNEL_BAN("channel.ban","1"),
    CHANNEL_UNBAN("channel.unban","1"),
    CHANNEL_MODERATOR_ADD("channel.moderator.add","1"),
    CHANNEL_MODERATOR_REMOVE("channel.moderator.remove","1"),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_ADD("channel.channel_points_custom_reward.add","1"),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_UPDATE("channel.channel_points_custom_reward.update","1"),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REMOVE("channel.channel_points_custom_reward.remove","1"),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_ADD("channel.channel_points_custom_reward_redemption.add","1"),
    CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_UPDATE("channel.channel_points_custom_reward_redemption.update","1"),
    CHANNEL_POLL_BEGIN("channel.poll.begin","1"),
    CHANNEL_POLL_PROGRESS("channel.poll.progress","1"),
    CHANNEL_POLL_END("channel.poll.end","1"),
    CHANNEL_PREDICTION_BEGIN("channel.prediction.begin","1"),
    CHANNEL_PREDICTION_PROGRESS("channel.prediction.progress","1"),
    CHANNEL_PREDICTION_LOCK("channel.prediction.lock","1"),
    CHANNEL_PREDICTION_END("channel.prediction.end","1"),
    EXTENSION_BITS_TRANSACTION_CREATE("extension.bits_transaction.create","1"),
    CHANNEL_HYPE_TRAIN_BEGIN("channel.hype_train.begin","1"),
    CHANNEL_HYPE_TRAIN_PROGRESS("channel.hype_train.progress","1"),
    CHANNEL_HYPE_TRAIN_END("channel.hype_train.end","1"),
    STREAM_ONLINE("stream.online","1"),
    STREAM_OFFLINE("stream.offline","1"),
    USER_AUTHORIZATION_REVOKE("user.authorization.revoke","1"),
    USER_UPDATE("user.update","1"),
    ;

    @Getter
    private final @NonNull String identification;

    @Getter
    private final @NonNull String version;
}
