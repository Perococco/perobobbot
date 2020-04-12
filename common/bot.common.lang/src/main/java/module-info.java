import bot.common.lang.FactoryProvider;
import bot.common.lang.IdentityFactory;
import perococco.bot.common.lang.PerococcoIdentity;

/**
 * @author perococco
 **/
module bot.common.lang {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires com.google.common;

    exports bot.common.lang;

    uses IdentityFactory;
    uses FactoryProvider.WithLifeCycle;
    uses FactoryProvider.Basic;
    provides IdentityFactory with PerococcoIdentity;
}
