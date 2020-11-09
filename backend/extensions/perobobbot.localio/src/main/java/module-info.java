import perobobbot.common.lang.Packages;
import perobobbot.consoleio.spring.LocalIOPackages;

module bot.consoleio {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.common.lang;
    requires spring.context;
    requires perobobbot.common.command;
    requires perobobbot.access;


    opens perobobbot.consoleio.spring to spring.core,spring.beans,spring.context;
    opens perobobbot.consoleio to spring.beans;

    provides Packages with LocalIOPackages;
}