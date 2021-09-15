import perobobbot.plugin.PerobobbotPlugin;

module perobobbot.plugin {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.chat.core;
    requires perobobbot.messaging;
    requires perobobbot.command;
    requires perobobbot.data.service;

    requires com.google.common;
    requires jplugman.annotation;
    requires java.servlet;
    requires spring.webmvc;

    exports perobobbot.plugin;

    uses PerobobbotPlugin;


}
