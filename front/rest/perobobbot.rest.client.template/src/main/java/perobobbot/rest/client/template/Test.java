package perobobbot.rest.client.template;

import perobobbot.security.com.Credential;
import perococco.perobobbot.rest.client.template.PeroRestTemplateClientManager;

import java.util.concurrent.ExecutionException;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        testRest();
    }

    public static void testRest() throws ExecutionException, InterruptedException {
        final var client = new PeroRestTemplateClientManager("https://localhost:8443/api");

        final var users = client.login(new Credential("admin","admin"))
              .thenCompose(s -> client.userClient().listAllUsers())
              .toCompletableFuture().get();

        System.out.println(users);

    }
}
