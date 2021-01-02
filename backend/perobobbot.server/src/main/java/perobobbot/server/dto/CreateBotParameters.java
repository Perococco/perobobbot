package perobobbot.server.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateBotParameters {

    @NonNull String name;
}
