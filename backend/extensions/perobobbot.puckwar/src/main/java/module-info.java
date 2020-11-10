import perobobbot.common.lang.Packages;
import perobobbot.puckwar.spring.PuckWarConfiguration;

module perobobbot.ext.puckwar {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.overlay.api;
    requires perobobbot.common.lang;
    requires perobobbot.extension;
    requires perobobbot.common.command;
    requires perobobbot.access;
    requires spring.context;
    requires perobobbot.common.math;


    provides Packages with PuckWarConfiguration;

    opens perobobbot.puckwar.spring to spring.core, spring.beans, spring.context;
}