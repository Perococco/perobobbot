import perobobbot.lang.Plugin;
import perobobbot.store.spring.BankExtensionFactory;

module perobobbot.ext.store {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.extension;
    requires perobobbot.chat.core;
    requires perobobbot.command;
    requires perobobbot.access;
    requires com.google.common;
    requires spring.context;

    provides Plugin with BankExtensionFactory;

    opens perobobbot.store.spring to spring.core, spring.beans, spring.context;
}
