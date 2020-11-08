import perobobbot.common.lang.Packages;
import perobobbot.greeter.spring.GreeterPackages;

module perobobbot.greeter {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;
    requires org.apache.logging.log4j;

    requires perobobbot.common.lang;
    requires perobobbot.chat.core;
    requires perobobbot.common.messaging;
    requires perobobbot.extension;

    requires spring.context;

    opens perobobbot.greeter.spring to spring.core,spring.beans,spring.context;

    provides Packages with GreeterPackages;
}