import perobobbot.plugin.PerobobbotPlugin;

module perobobbot.plugin {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.chat.core;
    requires perobobbot.messaging;
    requires perobobbot.overlay.api;

    requires com.google.common;
    requires perobobbot.command;
    requires perobobbot.data.service;

    exports perobobbot.plugin;

    uses PerobobbotPlugin;


}
