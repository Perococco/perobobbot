import perobobbot.plugin.PerobobbotPluginData;

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
    requires spring.context;

    exports perobobbot.plugin;

    uses PerobobbotPluginData;


}
