module perobobbot.twitch.api {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.http;

    requires com.fasterxml.jackson.databind;

    exports perobobbot.twitch.api;
    exports perobobbot.twitch.api.deser;

    opens perobobbot.twitch.api to com.fasterxml.jackson.databind;

}