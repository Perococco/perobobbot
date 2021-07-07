package perobobbot.twitch.client.webclient.channelpoints;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.http.Page;
import perobobbot.oauth.OAuthWebClientFactory;
import perobobbot.oauth.UserApiToken;
import perobobbot.twitch.client.api.QueryParameterBuilder;
import perobobbot.twitch.client.api.channelpoints.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RequiredArgsConstructor
public class WebClientTwitchServiceChannelPoints implements TwitchServiceChannelPointsWithToken {

    public static final String REWARDS_ENDPOINT = "/channel_points/custom_rewards";
    public static final String REDEMPTIONS_ENDPOINT = REWARDS_ENDPOINT+"/redemptions";

    private final @NonNull OAuthWebClientFactory factory;

    @Override
    public @NonNull Mono<CustomReward> createCustomReward(@NonNull UserApiToken userApiToken, @NonNull CreateCustomRewardParameter parameter) {
        return factory.create(userApiToken)
                      .post(REWARDS_ENDPOINT, "broadcaster_id", userApiToken.getUserId())
                      .body(parameter, CreateCustomRewardParameter.class)
                      .retrieve()
                      .bodyToMono(WrappedCustomRewards.class)
                      .map(r -> r.getData()[0]);
    }

    @Override
    public @NonNull Flux<CustomReward> getCustomReward(@NonNull UserApiToken userApiToken, @NonNull GetCustomRewardsParameter parameter) {
        final var queryParameters = QueryParameterBuilder.builder()
                                                         .setValues("id", parameter.getIds())
                                                         .setValue("only_manageable_rewards", parameter.isOnlyManageableRewards())
                                                         .setValue("broadcaster_id", userApiToken.getUserId())
                                                         .build();

        return factory.create(userApiToken)
                      .get(REWARDS_ENDPOINT, queryParameters)
                      .retrieve()
                      .bodyToMono(WrappedCustomRewards.class)
                      .flatMapIterable(response -> Arrays.asList(response.getData()));
    }

    @Override
    public @NonNull Mono<CustomReward> updateCustomReward(@NonNull UserApiToken userApiToken, @NonNull String customRewardId, @NonNull UpdateCustomRewardParameter parameter) {
        final var queryParameters = QueryParameterBuilder.builder()
                                                         .setValue("broadcaster_id",userApiToken.getUserId())
                                                         .setValue("id",customRewardId)
                                                         .build();

        return factory.create(userApiToken)
                      .patch(REWARDS_ENDPOINT, queryParameters)
                      .body(parameter, UpdateCustomRewardParameter.class)
                      .retrieve()
                      .bodyToMono(WrappedCustomRewards.class)
                      .map(r -> r.getData()[0]);
    }

    @Override
    public @NonNull Mono<?> deleteCustomRewards(@NonNull UserApiToken userApiToken, @NonNull String customRewardId) {
        final var queryParameters = QueryParameterBuilder.builder()
                                                         .setValue("broadcaster_id", userApiToken.getUserId())
                                                         .setValue("id", customRewardId)
                                                         .build();

        return factory.create(userApiToken)
                      .delete(REWARDS_ENDPOINT, queryParameters)
                      .retrieve()
                      .toBodilessEntity();
    }


    @Override
    public @NonNull Mono<Page<CustomRewardRedemption[],GetCustomRewardRedemptionParameter>> getCustomRewardRedemption(@NonNull UserApiToken userApiToken, @NonNull GetCustomRewardRedemptionParameter parameter) {
        final var queryParameters = QueryParameterBuilder.builder()
                                                         .setValue("broadcaster_id", userApiToken.getUserId())
                                                         .setValue("reward_id", parameter.getRewardId())
                                                         .setValue("id", parameter.getId().orElse(null))
                                                         .setValue("status", parameter.getStatus().orElse(null))
                                                         .setValue("sort", parameter.getSort().orElse(null))
                                                         .setValue("after", parameter.getAfter().orElse(null))
                                                         .setValue("first", parameter.getFirst().orElse(null))
                                                         .build();

        return factory.create(userApiToken)
                .get(REDEMPTIONS_ENDPOINT,queryParameters)
                .retrieve()
                .bodyToMono(RedemptionsResponse.class)
                .map(r -> r.createPage(parameter));
    }

    @Override
    public @NonNull Mono<CustomRewardRedemption[]> updateRedemptionStatus(@NonNull UserApiToken userApiToken, @NonNull String rewardId, @NonNull String[] redemptionsIds, @NonNull UpdateRedemptionStatus parameter) {
        final var queryParameters = QueryParameterBuilder.builder()
                                                         .setValue("broadcaster_id",userApiToken.getUserId())
                                                         .setValue("reward_id",rewardId)
                                                         .setValues("id",redemptionsIds)
                                                         .build();

        return factory.create(userApiToken)
                .patch(REDEMPTIONS_ENDPOINT,queryParameters)
                .body(parameter,UpdateRedemptionStatus.class)
                .retrieve()
                .bodyToMono(RedemptionsResponse.class)
                .map(r -> r.getData());
    }
}
