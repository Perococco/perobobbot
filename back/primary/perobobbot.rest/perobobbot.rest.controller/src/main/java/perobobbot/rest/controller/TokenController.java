package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.Platform;
import perobobbot.rest.com.RestUserToken;
import perobobbot.security.com.BotUser;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final @NonNull @SecuredService OAuthService oauthService;

    @GetMapping("")
    public @NonNull ImmutableList<RestUserToken> getUserToken(@NonNull @AuthenticationPrincipal BotUser principal) {
        return oauthService.getAllUserTokens(principal.getUsername())
                           .stream()
                           .map(RestUserToken::fromUserTokenView)
                           .collect(ImmutableList.toImmutableList());
    }

    @GetMapping("/{tokenId}")
    public @NonNull RestUserToken getUserToken(@NonNull @PathVariable UUID tokenId) {
        return RestUserToken.fromUserTokenView(oauthService.getUserToken(tokenId));
    }

    @DeleteMapping("/{tokenId}")
    public void deleteUserToken(@NonNull @PathVariable UUID tokenId) {
        oauthService.deleteUserToken(tokenId);
    }

    @PutMapping("/{tokenId}")
    public RestUserToken refreshUserToken(@NonNull @PathVariable UUID tokenId) {
        return RestUserToken.fromUserTokenView(oauthService.refreshUserToken(tokenId));
    }

    @PostMapping("/oauth")
    public @NonNull URI initiateOAuth(@NonNull @AuthenticationPrincipal BotUser principal, @RequestBody @NonNull Platform platform) {
        final var login = principal.getUsername();
        final var oauthInfo = oauthService.createUserToken(login,platform);
        return oauthInfo.getOauthURI();
    }

}
