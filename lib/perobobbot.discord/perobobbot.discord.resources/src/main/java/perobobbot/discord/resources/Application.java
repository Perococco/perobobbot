package perobobbot.discord.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Application {

    String id;//	snowflake	the id of the app
    String name;//	the name of the app
    String icon;//the icon hash of the app
    String description; //the description of the app
    String[] rpcOrigins;//an array of rpc origin urls, if rpc is enabled
    boolean botPublic;//	when false only app owner can join the app's bot to guilds
    boolean botRequireCodeGrant;//	when true the app's bot will only join upon completion of the full oauth2 code grant flow
    String termsOfServiceUrl;//the url of the app's terms of service
    String privacyPolicyUrl;//	the url of the app's privacy policy
    DiscordUser owner;//partial user object containing info on the owner of the application
//    String summary;//if this application is a game sold on Discord, this field will be the summary field for the store page of its primary sku
//    String verifyKey;//the hex encoded key for verification in interactions and the GameSDK's GetTicket
//    team	?team object	if the application belongs to a team, this will be a list of the members of that team
//    guild_id?	snowflake	if this application is a game sold on Discord, this field will be the guild to which it has been linked
//    primary_sku_id?	snowflake	if this application is a game sold on Discord, this field will be the id of the "Game SKU" that is created, if exists
//    slug?	string	if this application is a game sold on Discord, this field will be the URL slug that links to the store page
//    cover_image?	string	the application's default rich presence invite cover image hash
//    flags?	integer	the application's public flags
}
