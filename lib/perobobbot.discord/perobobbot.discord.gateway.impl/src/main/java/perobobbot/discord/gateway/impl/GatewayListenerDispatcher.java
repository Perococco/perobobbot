package perobobbot.discord.gateway.impl;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.lang.Listeners;
import perobobbot.lang.Subscription;
import perobobbot.discord.resources.GatewayEvent;
import perobobot.discord.gateway.api.GatewayEventListener;

public class GatewayListenerDispatcher implements GatewayEventListener {

    private final @NonNull Listeners<GatewayEventListener> listeners = new Listeners<>();

    @Override
    public void onGatewayEvent(@NonNull GatewayEvent event) {
        listeners.warnListeners(l -> l.onGatewayEvent(event));
    }

    @Synchronized
    public Subscription addListener(@NonNull GatewayEventListener listener) {
        return listeners.addListener(listener);
    }
}
