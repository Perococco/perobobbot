module bot.program.core {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires bot.common.lang;
    requires bot.chat.advanced;
    requires org.apache.logging.log4j;


    exports bot.program.core;
}