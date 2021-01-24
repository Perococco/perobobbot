package perococco.perobobbot.frontfx.gui.style;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.fx.StyleManager;
import perobobbot.lang.MapTool;
import perobobbot.lang.Theme;

import java.util.stream.Stream;

@Configuration
public class StyleConfiguration {

    @NonNull
    @Getter
    private final ImmutableMap<String, Theme> themes;

    public StyleConfiguration() {
        final ThemeConstructor themeConstructor = new ThemeConstructor();
        this.themes = Stream.of(Theme.EMPTY,themeConstructor.create("Legacy"))
                            .collect(MapTool.collector(Theme::getName));
    }

    @NonNull
    public Theme getInitialTheme() {
        return themes.getOrDefault("Legacy",Theme.EMPTY);
    }

    @Bean
    public StyleManager styleManager() {
        final Theme theme = getInitialTheme();
        final StyleManager styleManager = StyleManager.simple();
        styleManager.applyTheme(theme);
        return styleManager;
    }

}
