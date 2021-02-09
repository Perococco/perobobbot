import perobobbot.dungeon.spring.DungeonExtensionFactory;
import perobobbot.lang.Plugin;

module perobobbot.ext.dungeon {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires perobobbot.lang;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires com.google.common;
    requires spring.context;
    requires perobobbot.access;
    requires perobobbot.command;
    requires jdgen.generator;
    requires jdgen.api;

    provides Plugin with DungeonExtensionFactory;

    opens perobobbot.dungeon.spring to spring.core, spring.beans, spring.context;


}
