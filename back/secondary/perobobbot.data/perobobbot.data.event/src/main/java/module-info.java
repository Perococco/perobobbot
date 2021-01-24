import perobobbot.data.event.DataEventConfiguration;
import perobobbot.lang.Plugin;

module perobobbot.data.event {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;
    requires perobobbot.data.service;
    requires spring.context;
    requires com.google.common;

    requires spring.integration.core;
    requires spring.messaging;

    opens perobobbot.data.event to spring.core,spring.context,spring.beans;
    opens perobobbot.data.event.service to spring.core,spring.context,spring.beans;

    exports perobobbot.data.event;
    exports perobobbot.data.event.service;
    provides Plugin with DataEventConfiguration;
}
