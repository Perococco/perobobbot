package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.SecuredService;
import perobobbot.rest.com.RestUserToken;

import java.util.UUID;

@RestController
@RequestMapping("/api/credentials")
@RequiredArgsConstructor
public class TokenController {

    private final @NonNull
    @SecuredService
    OAuthService OAuthService;

    @GetMapping("")
    public @NonNull ImmutableList<RestUserToken> getUserToken(@NonNull @AuthenticationPrincipal UserDetails principal) {
        return OAuthService.getAllUserTokens(principal.getUsername())
                           .stream()
                           .map(RestUserToken::fromUserTokenView)
                           .collect(ImmutableList.toImmutableList());
    }

    @GetMapping("/{tokenId}")
    public @NonNull RestUserToken getUserToken(@NonNull @PathVariable UUID tokenId) {
        return RestUserToken.fromUserTokenView(OAuthService.getUserToken(tokenId));
    }

    @DeleteMapping("/{tokenId}")
    public void deleteUserToken(@NonNull @PathVariable UUID tokenId) {
        OAuthService.deleteUserToken(tokenId);

    }

}
