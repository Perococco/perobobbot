import perobobbot.lang.Packages;
import perobobbot.localio.spring.LocalIOPackages;

module bot.consoleio {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;
    requires spring.context;
    requires perobobbot.common.command;
    requires perobobbot.access;


    opens perobobbot.localio.spring to spring.core,spring.beans,spring.context;
    opens perobobbot.localio to spring.beans;

    provides Packages with LocalIOPackages;
}