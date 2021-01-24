package perococco.perobobbot.fx;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.fx.StyleManager;
import perobobbot.lang.Theme;
import perobobbot.lang.fp.Function1;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleStyleManager implements StyleManager {

    private final List<Stylable<?>> scenes=  new ArrayList<>();

    private Theme currentTheme = Theme.EMPTY;

    @NonNull
    public Scene addStylable(@NonNull Scene scene) {
        this.scenes.add(Stylable.forScene(scene));
        if (!currentTheme.isEmpty()) {
            scene.getStylesheets().add(currentTheme.getThemeUrl());
        }
        return scene;
    }

    @Override
    public @NonNull void addStylable(@NonNull Parent parent) {
        this.scenes.add(Stylable.forParent(parent));
        if (!currentTheme.isEmpty()) {
            parent.getStylesheets().add(currentTheme.getThemeUrl());
        }
    }

    @Override
    public void applyTheme(@NonNull Theme theme) {
        final Iterator<Stylable<?>> sceneIterator = scenes.iterator();
        final Theme oldTheme = currentTheme;
        final Theme newTheme = theme;
        while (sceneIterator.hasNext()) {
            final Stylable<?> stylable = sceneIterator.next();
            final boolean styleSet = stylable.tryToSetStyle(oldTheme,newTheme);
            if (!styleSet) {
                sceneIterator.remove();
            }
        }
        this.currentTheme = theme;
    }

    @RequiredArgsConstructor
    private static class Stylable<T> {

        @NonNull
        private final Reference<T> reference;

        @NonNull
        private final Function1<? super T, ? extends ObservableList<String>> stylesheets;

        @NonNull
        public static Stylable<Scene> forScene(@NonNull Scene scene) {
            return new Stylable<>(new WeakReference<>(scene), Scene::getStylesheets);
        }

        @NonNull
        public static Stylable<Parent> forParent(@NonNull Parent parent) {
            return new Stylable<>(new WeakReference<>(parent), Parent::getStylesheets);
        }

        public boolean tryToSetStyle(@NonNull Theme oldTheme, @NonNull Theme newTheme) {
            final T value = reference.get();
            if (value != null) {
                updateScene(stylesheets.f(value),oldTheme,newTheme);
            }
            return value != null;
        }


        private void updateScene(@NonNull ObservableList<String> stylesheets, @NonNull Theme oldTheme, @NonNull Theme newTheme) {
            Platform.runLater(() -> {
                if (!oldTheme.isEmpty()) {
                    stylesheets.remove(oldTheme.getThemeUrl());
                }
                if (!newTheme.isEmpty()) {
                    stylesheets.add(newTheme.getThemeUrl());
                }
            });
        }

    }
}
