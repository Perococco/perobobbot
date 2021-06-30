package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.rest.com.OAuthProcessParameter;
import perobobbot.rest.com.RestUserToken;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final @NonNull @SecuredService OAuthService oauthService;

    @GetMapping("")
    public @NonNull ImmutableList<RestUserToken> getUserToken(@NonNull @AuthenticationPrincipal UserDetails principal) {
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
    public @NonNull URI initiateOAuth(@NonNull @AuthenticationPrincipal UserDetails principal, @RequestBody @NonNull OAuthProcessParameter parameter) {
        final var login = principal.getUsername();
        final var oauthInfo = oauthService.authenticateUser(login,parameter.getScopes(),parameter.getPlatform());
        return oauthInfo.getOauthURI();
    }

}
