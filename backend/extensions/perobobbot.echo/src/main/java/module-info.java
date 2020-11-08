import perobobbot.common.lang.Packages;
import perobobbot.echo.spring.EchoConfiguration;

module perobobbot.echo {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.extension;
    requires perobobbot.chat.core;
    requires perobobbot.common.command;
    requires perobobbot.access;

    requires spring.context;
    requires com.google.common;

    provides Packages with EchoConfiguration;

    opens perobobbot.echo.spring to spring.core,spring.beans,spring.context;
}