package perobobbot.twitch.client.api.channelpoints;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.client.api.TwitchApiPayload;
import perobobbot.twitch.eventsub.api.event.GlobalCooldown;
import perobobbot.twitch.api.Image;
import perobobbot.twitch.eventsub.api.event.Limit;

import java.time.Instant;

@Value
public class CustomReward implements TwitchApiPayload {

    @NonNull UserInfo broadcaster;
    @NonNull String id;
    Image image;
    @NonNull String backgroundColor;
    boolean enabled;
    int cost;
    @NonNull String title;
    @NonNull String prompt;
    boolean userInputRequired;

    MaxPerStreamSetting maxPerStreamSetting;
    MaxPerUserPerStreamSetting maxPerUserPerStreamSetting;
    GlobalCooldownSetting globalCooldownSetting;

    boolean paused;
    boolean inStock;
    @NonNull Image defaultImage;
    boolean shouldRedemptionsSkipRequestQueue;
    Integer redemptionsRedeemedCurrentStream;
    Instant cooldownExpiresAt;

//    broadcaster_id 	string 	ID of the channel the reward is for.
//    broadcaster_login 	string 	Broadcaster’s user login name.
//    broadcaster_name 	string 	Display name of the channel the reward is for.
//    id 	string 	ID of the reward.
//    title 	string 	The title of the reward.
//    prompt 	string 	The prompt for the viewer when they are redeeming the reward.
//    cost 	integer 	The cost of the reward.
//    image 	object 	Set of custom images of 1x, 2x and 4x sizes for the reward { url_1x: string, url_2x: string, url_4x: string }, can be null if no images have been uploaded
//    default_image 	object 	Set of default images of 1x, 2x and 4x sizes for the reward { url_1x: string, url_2x: string, url_4x: string }
//    background_color 	string 	Custom background color for the reward. Format: Hex with # prefix. Example: #00E5CB.
//            is_enabled 	boolean 	Is the reward currently enabled, if false the reward won’t show up to viewers
//    is_user_input_required 	boolean 	Does the user need to enter information when redeeming the reward
//    max_per_stream_setting 	object 	Whether a maximum per stream is enabled and what the maximum is. { is_enabled: bool, max_per_stream: int }
//    max_per_user_per_stream_setting 	object 	Whether a maximum per user per stream is enabled and what the maximum is. { is_enabled: bool, max_per_user_per_stream: int }
//    global_cooldown_setting 	object 	Whether a cooldown is enabled and what the cooldown is. { is_enabled: bool, global_cooldown_seconds: int }
//    is_paused 	boolean 	Is the reward currently paused, if true viewers can’t redeem
//    is_in_stock 	boolean 	Is the reward currently in stock, if false viewers can’t redeem
//    should_redemptions_skip_request_queue 	boolean 	Should redemptions be set to FULFILLED status immediately when redeemed and skip the request queue instead of the normal UNFULFILLED status.
//    redemptions_redeemed_current_stream 	integer 	The number of redemptions redeemed during the current live stream. Counts against the max_per_stream_setting limit. Null if the broadcasters stream isn’t live or max_per_stream_setting isn’t enabled.
//    cooldown_expires_at 	string 	Timestamp of the cooldown expiration. Null if the reward isn’t on cooldown.
}
