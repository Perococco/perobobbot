import perobobbot.lang.AsyncIdentityFactory;
import perobobbot.lang.FactoryProvider;
import perobobbot.lang.JsonModuleProvider;
import perobobbot.lang.LangModule;
import perococco.perobobbot.common.lang.PerococcoAsyncIdentity;

/**
 * @author perococco
 **/
module perobobbot.lang {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires com.fasterxml.jackson.databind;
    requires com.google.common;
    requires reactor.core;


    exports perobobbot.lang;
    exports perobobbot.lang.fp;
    exports perobobbot.lang.token;

    uses AsyncIdentityFactory;
    uses FactoryProvider.WithLifeCycle;
    uses FactoryProvider.Basic;


    provides AsyncIdentityFactory with PerococcoAsyncIdentity;
    provides JsonModuleProvider with LangModule;

    opens perobobbot.lang to com.fasterxml.jackson.databind;
    opens perobobbot.lang.token to com.fasterxml.jackson.databind;
}
