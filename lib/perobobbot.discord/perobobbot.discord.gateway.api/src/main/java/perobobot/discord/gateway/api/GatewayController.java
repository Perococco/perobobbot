package perobobot.discord.gateway.api;

public interface GatewayController extends Gateway {

    boolean isConnected();

    void connect();

    void disconnect();

}
