package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.TokenService;
import perobobbot.rest.com.RestUserToken;

import java.util.UUID;

@RestController
@RequestMapping("/api/credentials")
@RequiredArgsConstructor
public class TokenController {

    private final @NonNull
    @SecuredService
    TokenService tokenService;

    @GetMapping("")
    public @NonNull ImmutableList<RestUserToken> getUserToken(@NonNull @AuthenticationPrincipal UserDetails principal) {
        return tokenService.getUserTokens(principal.getUsername())
                           .stream()
                           .map(RestUserToken::fromUserTokenView)
                           .collect(ImmutableList.toImmutableList());
    }

    @GetMapping("/{id}")
    public @NonNull RestUserToken getUserToken(@NonNull @PathVariable UUID id) {
        return RestUserToken.fromUserTokenView(tokenService.getUserToken(id));
    }

    @DeleteMapping("/{tokenId}")
    public void deleteUserToken(@NonNull @PathVariable UUID tokenId) {
        tokenService.deleteUserToken(tokenId);

    }

}
