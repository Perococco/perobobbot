package perococco.perobobbot.program.sample;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.Identity;
import perobobbot.common.lang.IdentityHashSet;
import perobobbot.common.lang.ImmutableEntry;
import perobobbot.common.lang.SetTool;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.MessageHandler;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class HelloMessageHandler implements MessageHandler {

    private static final String e = "[eе]";
    private static final String o = "[oοоօⲟ]";
    private static final String s = "[sꜱ]";
    private static final String a = "[aа]";
    private static final String h = "[hһҺ]";
    private static final String c = "[cᴄ]";
    private static final String l = "l";
    private static final String u = "u";
    private static final String t = "t";
    private static final String b = "b";
    private static final String j = "j";
    private static final String n = "n";
    private static final String i = "[iі]";
    private static final String r = "r";

    private static final Pattern SALUT_PATTERN = Pattern.compile(s + a + l + u + t, Pattern.CASE_INSENSITIVE);
    private static final Pattern HELLO_PATTERN = Pattern.compile(h + e + l + l + o, Pattern.CASE_INSENSITIVE);
    private static final Pattern BONJOUR_PATTERN = Pattern.compile(b + o + n + j + o + u + r, Pattern.CASE_INSENSITIVE);
    private static final Pattern BONSOIR_PATTERN = Pattern.compile(b + o + n + s + o + i + r, Pattern.CASE_INSENSITIVE);
    private static final Pattern COUCOU_PATTERN = Pattern.compile(c + o + u + c + o + u, Pattern.CASE_INSENSITIVE);

    private static final ImmutableList<Pattern> FRENCH_PATTERNS = ImmutableList.of(
            SALUT_PATTERN,
            HELLO_PATTERN,
            BONJOUR_PATTERN,
            BONSOIR_PATTERN,
            COUCOU_PATTERN
    );

    @NonNull
    private final Identity<ImmutableSet<String>> alreadyGreeted;

    @Override
    public @NonNull ExecutionContext handleMessage(@NonNull ExecutionContext context) {
        if (!alreadyGreeted.getState().contains(context.getExecutingUserId())) {
            if (containsHello(context.getMessage())) {
                alreadyGreeted.mutate(s -> SetTool.add(s,context.getExecutingUserId()));
                context.print("Salut @" + context.getExecutingUser().getUserName());
            }
        }
        return context;
    }

    private boolean containsHello(@NonNull String message) {
        return Arrays.stream(message.split("\\s"))
                     .anyMatch(this::isHelloWorld);
    }

    private boolean isHelloWorld(@NonNull String word) {
        return FRENCH_PATTERNS.stream().anyMatch(p -> p.matcher(word).matches());
    }
}
