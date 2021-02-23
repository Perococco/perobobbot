import perobobbot.benchmark.BenchmarkExtensionFactory;
import perobobbot.plugin.Plugin;

module perobobbot.ext.benchmark {
    requires static lombok;
    requires java.desktop;


    requires perobobbot.lang;
    requires perobobbot.plugin;
    requires perobobbot.overlay.api;
    requires perobobbot.extension;
    requires perobobbot.access;
    requires perobobbot.command;
    requires perobobbot.physics;

    requires com.google.common;

    provides Plugin with BenchmarkExtensionFactory;

}
