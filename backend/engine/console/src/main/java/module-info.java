import perobobbot.common.lang.Packages;
import perobobbot.console.spring.ConsoleIOPackages;

module bot.console {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.common.lang;
    requires spring.context;


    exports perobobbot.console;

    opens perobobbot.console.spring to spring.core,spring.beans,spring.context;

    provides Packages with ConsoleIOPackages;
}