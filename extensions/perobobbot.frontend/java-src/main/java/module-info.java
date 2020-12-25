import perobobbot.frontend.spring.FrontEndConfig;
import perobobbot.lang.Packages;

module perobobbot.frontend {
    requires spring.context;
    requires spring.webmvc;
    requires perobobbot.lang;

    opens perobobbot.frontend.spring;

    provides Packages with FrontEndConfig;
}