import perobobbot.benchmark.spring.BenchmarkConfiguration;
import perobobbot.common.lang.Packages;

module perobobbot.ext.benchmark {
    requires static lombok;
    requires java.desktop;


    requires perobobbot.common.lang;
    requires perobobbot.common.math;
    requires perobobbot.overlay.api;
    requires com.google.common;
    requires perobobbot.extension;
    requires spring.context;
    requires perobobbot.access;
    requires perobobbot.common.command;


    provides Packages with BenchmarkConfiguration;

    opens perobobbot.benchmark.spring to spring.core, spring.beans,spring.context;
}