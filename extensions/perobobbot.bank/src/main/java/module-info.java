import perobobbot.plugin.Plugin;
import perobobbot.store.BankExtensionFactory;

module perobobbot.ext.store {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.plugin;
    requires perobobbot.extension;
    requires perobobbot.chat.core;
    requires perobobbot.command;
    requires perobobbot.access;
    requires com.google.common;
    requires spring.context;

    provides Plugin with BankExtensionFactory;

}
