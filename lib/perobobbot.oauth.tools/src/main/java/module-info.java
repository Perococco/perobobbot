module perobobbot.oauth.tools {
    requires static lombok;
    requires org.apache.logging.log4j;
    requires java.desktop;
    requires perobobbot.data.service;
    requires spring.web;
    requires spring.webflux;
    requires perobobbot.http;

    exports perobobbot.oauth.tools;

}
