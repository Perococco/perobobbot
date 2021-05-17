module perobobbot.proxy {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires org.apache.logging.log4j;

    exports perobobbot.proxy;
    exports perobobbot.proxy._private;
}
