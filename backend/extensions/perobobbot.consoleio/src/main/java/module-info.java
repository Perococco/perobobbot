import perobobbot.common.lang.Packages;
import perobobbot.consoleio.spring.ConsoleIOPackages;

module bot.consoleio {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.common.lang;
    requires spring.context;


    exports perobobbot.consoleio;

    opens perobobbot.consoleio.spring to spring.core,spring.beans,spring.context;

    provides Packages with ConsoleIOPackages;
}