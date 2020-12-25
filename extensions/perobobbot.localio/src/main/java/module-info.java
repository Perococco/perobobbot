import perobobbot.lang.Packages;
import perobobbot.localio.spring.LocalIOPackages;

module perobobbot.consoleio {
    requires static lombok;
    requires java.desktop;
    requires spring.context;

    requires perobobbot.lang;
    requires perobobbot.chat.core;
    requires perobobbot.command;
    requires perobobbot.access;

    requires com.google.common;

    opens perobobbot.localio.spring to spring.core,spring.beans,spring.context;
    opens perobobbot.localio to spring.beans;

    provides Packages with LocalIOPackages;
}