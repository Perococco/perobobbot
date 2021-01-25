module perobobbot.rest.client.api {
    uses perobobbot.rest.client.ClientManager;
    requires static lombok;
    requires java.desktop;
    requires com.google.common;
    requires transitive perobobbot.rest.com;
    requires transitive perobobbot.data.com;


    exports perobobbot.rest.client;
}
