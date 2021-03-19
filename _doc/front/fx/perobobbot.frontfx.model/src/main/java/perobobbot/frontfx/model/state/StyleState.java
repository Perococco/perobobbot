package perobobbot.frontfx.model.state;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Theme;

import java.util.Optional;

/**
 * Contains information about the current applied theme but also the list
 * of available themes
 */
@RequiredArgsConstructor
@Builder(toBuilder = true)
@Getter
public class StyleState {

    @NonNull
    private final ImmutableMap<String, Theme> themes;

    @NonNull
    private final String nameOfSelectedTheme;

    @NonNull
    public Optional<Theme> getSelectedTheme() {
        return Optional.ofNullable(themes.get(nameOfSelectedTheme));
    }

    public @NonNull StyleState withNewSelectedTheme(@NonNull String themeName) {
        final var theme = themes.get(themeName);
        if (theme == null) {
            return this;
        }
        return toBuilder().nameOfSelectedTheme(themeName).build();
    }

}
