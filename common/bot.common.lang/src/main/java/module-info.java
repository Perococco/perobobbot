import bot.common.lang.FactoryProvider;
import bot.common.lang.AsyncIdentityFactory;
import perococco.bot.common.lang.PerococcoAsyncIdentity;

/**
 * @author perococco
 **/
module bot.common.lang {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires com.google.common;

    exports bot.common.lang;
    exports bot.common.lang.fp;

    uses AsyncIdentityFactory;
    uses FactoryProvider.WithLifeCycle;
    uses FactoryProvider.Basic;
    provides AsyncIdentityFactory with PerococcoAsyncIdentity;
}
