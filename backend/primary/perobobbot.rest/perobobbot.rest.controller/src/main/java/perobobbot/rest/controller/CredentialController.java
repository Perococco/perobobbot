package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.SecuredService;
import perobobbot.rest.com.RestCredentialInfo;
import perobobbot.rest.controller.converter.CredentialInfoConverter;

import java.util.UUID;

@RestController
@RequestMapping("/api/credentials")
@RequiredArgsConstructor
public class CredentialController {

    private final @NonNull
    @SecuredService
    CredentialService credentialService;

    @GetMapping("")
    public @NonNull ImmutableList<RestCredentialInfo> getCredentials(@NonNull @AuthenticationPrincipal UserDetails principal) {
        return credentialService.getCredentials(principal.getUsername())
                          .stream()
                          .filter(DataCredentialInfo::hasSecret)
                          .map(CredentialInfoConverter.INSTANCE)
                          .collect(ImmutableList.toImmutableList());
    }

    @GetMapping("/{id}")
    public @NonNull RestCredentialInfo getCredential(@NonNull @PathVariable UUID id) {
        return CredentialInfoConverter.INSTANCE.transform(credentialService.getCredential(id));
    }

    @DeleteMapping("/{id}")
    public void deleteCredential(@NonNull @PathVariable UUID id) {
        credentialService.deleteCredential(id);
    }

}
