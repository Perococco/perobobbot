import perobobbot.plugin.PerobobbotPlugin;

module perobobbot.plugin {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.chat.core;
    requires perobobbot.messaging;
    requires perobobbot.overlay.api;
    requires perobobbot.command;
    requires perobobbot.data.service;

    requires com.google.common;
    requires jplugman.annotation;

    exports perobobbot.plugin;

    uses PerobobbotPlugin;


}
