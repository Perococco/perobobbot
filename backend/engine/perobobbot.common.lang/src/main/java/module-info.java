import perobobbot.common.lang.AsyncIdentityFactory;
import perobobbot.common.lang.FactoryProvider;
import perococco.perobobbot.common.lang.PerococcoAsyncIdentity;

/**
 * @author perococco
 **/
module perobobbot.common.lang {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires com.google.common;

    exports perobobbot.common.lang;
    exports perobobbot.common.lang.fp;

    uses AsyncIdentityFactory;
    uses FactoryProvider.WithLifeCycle;
    uses FactoryProvider.Basic;
    provides AsyncIdentityFactory with PerococcoAsyncIdentity;
}
