open module perobobbot.data.service {
    requires static lombok;
    requires java.desktop;
    requires transitive perobobbot.data.com;

    requires com.google.common;

    requires spring.beans;
    requires spring.core;

//    opens perobobbot.data.service to spring.beans, spring.core, spring.context;

    exports perobobbot.data.service;
    exports perobobbot.data.service.proxy;
    exports perobobbot.data.service.event;
}
