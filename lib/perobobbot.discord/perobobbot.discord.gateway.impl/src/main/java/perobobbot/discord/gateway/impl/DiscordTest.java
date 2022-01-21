package perobobbot.discord.gateway.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.discord.gateway.impl.message.DefaultMessageMapper;
import perobobbot.discord.resources.GatewayBot;
import perobobbot.lang.Secret;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

public class DiscordTest {

    private final @NonNull Secret botToken;
    private final @NonNull WebClient webClient;
    private final @NonNull HttpClient client;

    private final @NonNull ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        final var botToken = Secret.with(Files.readAllLines(Path.of("/home/perococco/bot_token.txt")).get(0));

        new DiscordTest(botToken).launch();

        System.exit(1);

    }

    public DiscordTest(@NonNull Secret botToken) {
        this.botToken = botToken;
        SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
        ;
        this.client = new HttpClient(sslContextFactory);

        this.webClient = WebClient.builder().clientConnector(new JettyClientHttpConnector(client)).baseUrl("https://discord.com/api/v9").build();
    }

    public void launch() throws ExecutionException, InterruptedException {
        final var gatewayBot = webClient.get()
                                        .uri("/gateway/bot")
                                        .headers(h -> h.set("Authorization", "Bot " + botToken.getValue()))
                                        .retrieve()
                                        .bodyToMono(GatewayBot.class)
                                        .toFuture().get();

        if (gatewayBot.getShards() != 1) {
            System.out.println("Multiple shard bot not handled");
        }




        final var controller = new GatewayControllerImpl(gatewayBot, 8, botToken, new DefaultMessageMapper(objectMapper));

        controller.addGatewayEventListener(e -> System.out.println("Event " + e));

        System.out.println("Try to connect");
        try {
            controller.connect();

            System.out.println("Sleep for 30s");
            Thread.sleep(120_000);

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            System.out.println("Disconnect !");
            controller.disconnect();
        }

    }

}
