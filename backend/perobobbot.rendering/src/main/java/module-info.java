module perobobbot.rendering {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;

    requires com.google.common;

    exports perobobbot.rendering;
    exports perobobbot.rendering.graphics2d.element;
}