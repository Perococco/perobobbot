import perobobbot.data.security.DataSecurityConfiguration;
import perobobbot.plugin.Plugin;

module perobobbot.data.security {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.data.service;

    requires com.google.common;
    requires spring.context;
    requires spring.security.core;
    requires perobobbot.security.core;
    requires perobobbot.plugin;

    opens perobobbot.data.security to spring.core,spring.context,spring.beans,spring.secutiry;
    opens perobobbot.data.security.service to spring.core,spring.context,spring.beans,spring.secutiry;
    opens perobobbot.data.security.permission to spring.core,spring.context,spring.beans,spring.secutiry;


    exports perobobbot.data.security.service;

    provides Plugin with DataSecurityConfiguration;
}
