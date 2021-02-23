import perobobbot.echo.EchoExtensionFactory;
import perobobbot.plugin.Plugin;

module perobobbot.echo {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.extension;
    requires perobobbot.chat.core;
    requires perobobbot.command;
    requires perobobbot.access;

    requires com.google.common;
    requires perobobbot.plugin;

    provides Plugin with EchoExtensionFactory;

}
