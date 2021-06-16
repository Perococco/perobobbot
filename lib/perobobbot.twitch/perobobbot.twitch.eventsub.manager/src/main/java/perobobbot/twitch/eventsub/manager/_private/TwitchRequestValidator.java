package perobobbot.twitch.eventsub.manager._private;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.twitch.eventsub.api.Markers;
import perobobbot.twitch.eventsub.api.TwitchRequestContent;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TwitchRequestValidator {


    private static final String MAC_ALGORITHM = "HmacSHA256";


    public static @NonNull Optional<TwitchRequestContent<byte[]>> validate(@NonNull HttpServletRequest twitchRequest, @NonNull String secret) throws IOException, ServletException {
        return new TwitchRequestValidator(twitchRequest, secret).validate();
    }

    private final @NonNull HttpServletRequest request;
    private final @NonNull String secret;

    private String messageId;
    private String timeStamp;
    private String type;
    private String signature;
    private byte[] bodyContent;
    private byte[] signatureBytes;
    private String computedSignature;


    private @NonNull Optional<TwitchRequestContent<byte[]>> validate() throws IOException, ServletException {
        this.retrieveTwitchHeaders();
        if (!areAllHeadersDefined()) {
            return Optional.empty();
        }
        this.readRequestBodyContent();
        this.computeMacSignatureBytes();
        this.transformSignatureBytesToString();
        if (!isRequestValid()) {
            return Optional.empty();
        }
        return Optional.of(new TwitchRequestContent<>(type, messageId, Instant.parse(timeStamp), bodyContent));
    }

    private void retrieveTwitchHeaders() {
        messageId = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_ID.getHeader(request).orElse(null);
        timeStamp = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_TIMESTAMP.getHeader(request).orElse(null);
        type = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_TYPE.getHeader(request).orElse(null);
        signature = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_SIGNATURE.getHeader(request).orElse(null);
        LOG.debug(Markers.EVENT_SUB_MARKER, "Received request from Twitch");
        LOG.debug(Markers.EVENT_SUB_MARKER, " messageId : {} ", messageId);
        LOG.debug(Markers.EVENT_SUB_MARKER, " timeStamp : {} ", timeStamp);
        LOG.debug(Markers.EVENT_SUB_MARKER, " type      : {} ", type);
        LOG.debug(Markers.EVENT_SUB_MARKER, " signature : {} ", signature);
    }

    private boolean areAllHeadersDefined() {
        return messageId != null && timeStamp != null && type != null && signature != null;
    }


    private void readRequestBodyContent() throws IOException {
        this.bodyContent = request.getInputStream().readAllBytes();

    }

    private void computeMacSignatureBytes() throws ServletException {
        try {
            final var mac = Mac.getInstance(MAC_ALGORITHM);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.US_ASCII), MAC_ALGORITHM));
            mac.update(messageId.getBytes(StandardCharsets.US_ASCII));
            mac.update(timeStamp.getBytes(StandardCharsets.US_ASCII));
            mac.update(bodyContent);
            this.signatureBytes = mac.doFinal();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new ServletException(
                    "Could not find MAC algorithm " + MAC_ALGORITHM + " to check Twitch message signature", e);
        }
    }

    private void transformSignatureBytesToString() {
        computedSignature = IntStream.range(0, signatureBytes.length)
                                     .mapToObj(i -> String.format("%02x", signatureBytes[i]))
                                     .collect(Collectors.joining("", "sha256=", ""));
        LOG.debug(Markers.EVENT_SUB_MARKER, " Computed signature : {}", computedSignature);

    }

    private boolean isRequestValid() {
        return signature.equals(computedSignature);
    }
}
