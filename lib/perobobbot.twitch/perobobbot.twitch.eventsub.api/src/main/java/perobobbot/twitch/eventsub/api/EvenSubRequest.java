package perobobbot.twitch.eventsub.api;

import lombok.NonNull;

public interface EvenSubRequest {

    void accept(@NonNull Visitor visitor);

    interface Visitor {

        void visit(@NonNull EventSubNotification notification);
        void visit(@NonNull EventSubRevocation revocation);
        void visit(@NonNull EventSubVerification verification);

    }

}
