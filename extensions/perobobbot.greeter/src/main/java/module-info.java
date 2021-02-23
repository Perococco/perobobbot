import perobobbot.greeter.GreeterExtensionFactory;
import perobobbot.plugin.Plugin;

module perobobbot.greeter {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;
    requires org.apache.logging.log4j;

    requires perobobbot.lang;
    requires perobobbot.plugin;
    requires perobobbot.chat.core;
    requires perobobbot.messaging;
    requires perobobbot.extension;

    provides Plugin with GreeterExtensionFactory;
}
