import perobobbot.frontend.svelte.FrontEndConfig;
import perobobbot.plugin.Plugin;

module perobobbot.frontend {
    requires static lombok;

    requires perobobbot.lang;

    requires perobobbot.plugin;

    opens perobobbot.frontend.spring;

    provides Plugin with FrontEndConfig;
}
