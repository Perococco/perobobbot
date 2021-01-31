import perobobbot.lang.Plugin;
import perobobbot.sandbox.spring.SandboxExtensionFactory;

module perobobbot.ext.sandbox {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires perobobbot.lang;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires perobobbot.access;
    requires perobobbot.command;
    requires com.google.common;

    provides Plugin with SandboxExtensionFactory;
    opens perobobbot.sandbox.spring to spring.core, spring.beans, spring.context;

}
