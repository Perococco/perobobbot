import perobobbot.dungeon.spring.DungeonExtensionFactory;
import perobobbot.plugin.Plugin;

module perobobbot.ext.dungeon {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires perobobbot.lang;
    requires perobobbot.plugin;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires com.google.common;
    requires perobobbot.access;
    requires perobobbot.command;
    requires jdgen.generator;
    requires jdgen.api;

    provides Plugin with DungeonExtensionFactory;


}
