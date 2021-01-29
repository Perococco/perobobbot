module perobobbot.rendering {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;

    requires com.google.common;
    requires perobobbot.physics;
    requires perobobbot.timeline;

    exports perobobbot.rendering;
    exports perobobbot.rendering.histogram;
    exports perobobbot.rendering.graphics2d.element;
}
