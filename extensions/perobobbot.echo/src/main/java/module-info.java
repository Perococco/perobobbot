import perobobbot.echo.spring.EchoExtensionFactory;
import perobobbot.lang.Plugin;

module perobobbot.echo {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.extension;
    requires perobobbot.chat.core;
    requires perobobbot.command;
    requires perobobbot.access;

    requires spring.context;
    requires com.google.common;

    provides Plugin with EchoExtensionFactory;

    opens perobobbot.echo.spring to spring.core,spring.beans,spring.context;
}
