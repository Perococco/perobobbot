import perobobbot.frontend.spring.FrontEndConfig;
import perobobbot.lang.Plugin;

module perobobbot.frontend {
    requires spring.context;
    requires spring.webmvc;
    requires perobobbot.lang;

    opens perobobbot.frontend.spring;

    provides Plugin with FrontEndConfig;
}
