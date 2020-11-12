import perobobbot.lang.AsyncIdentityFactory;
import perobobbot.lang.FactoryProvider;
import perococco.perobobbot.common.lang.PerococcoAsyncIdentity;

/**
 * @author perococco
 **/
module perobobbot.lang {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires com.google.common;

    exports perobobbot.lang;
    exports perobobbot.lang.fp;

    uses AsyncIdentityFactory;
    uses FactoryProvider.WithLifeCycle;
    uses FactoryProvider.Basic;
    provides AsyncIdentityFactory with PerococcoAsyncIdentity;
}
