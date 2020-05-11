package perococco.perobobbot.common.irc;

import perobobbot.common.irc.Prefix;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
public class PrefixParser {

    @NonNull
    public Optional<Prefix> parse(@NonNull String prefixAsString) {
        final int userStart = prefixAsString.indexOf("!");
        final int hostStart = prefixAsString.lastIndexOf("@");

        final int lastIndex = prefixAsString.length()-1;

        if (userStart == 0
            || hostStart == 0
            || hostStart==lastIndex
            || userStart==lastIndex) {
            return Optional.empty();
        }

        final Prefix.Builder builder = Prefix.builder();

        if (userStart<0 && hostStart<0) {
            builder.nickOrServerName(prefixAsString);
        }
        else if (hostStart<0) {
            builder.nickOrServerName(prefixAsString.substring(0,userStart));
            builder.user(prefixAsString.substring(userStart+1));
        }
        else if (userStart<0) {
            builder.nickOrServerName(prefixAsString.substring(0,hostStart));
            builder.host(prefixAsString.substring(hostStart+1));
        } else {
            builder.nickOrServerName(prefixAsString.substring(0,userStart));
            builder.user(prefixAsString.substring(userStart+1,hostStart));
            builder.host(prefixAsString.substring(hostStart+1));
        }

        return Optional.of(builder.build());
    }

}
