package perobobbot.rest.controller;


import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.Client;
import perobobbot.lang.ListTool;
import perobobbot.lang.SafeClient;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final @NonNull @SecuredService ClientService clientService;

    @GetMapping("")
    public @NonNull ImmutableList<SafeClient> listClients() {
        return ListTool.map(clientService.findAllClients(), Client::stripSecret);
    }

    @PutMapping("")
    public @NonNull SafeClient createClient(@NonNull @RequestBody CreateClientParameter parameter) {
        final var client = clientService.createClient(parameter);
        return client.stripSecret();
    }
}
