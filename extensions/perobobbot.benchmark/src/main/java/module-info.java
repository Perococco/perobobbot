import perobobbot.benchmark.spring.BenchmarkExtensionFactory;
import perobobbot.lang.Packages;

module perobobbot.ext.benchmark {
    requires static lombok;
    requires java.desktop;


    requires perobobbot.lang;
    requires perobobbot.overlay.api;
    requires perobobbot.extension;
    requires perobobbot.access;
    requires perobobbot.command;
    requires perobobbot.physics;

    requires com.google.common;
    requires spring.context;

    provides Packages with BenchmarkExtensionFactory;

    opens perobobbot.benchmark.spring to spring.core, spring.beans,spring.context;
}