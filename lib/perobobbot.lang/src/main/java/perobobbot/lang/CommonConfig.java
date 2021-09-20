package perobobbot.lang;

public class CommonConfig {

    private static final boolean DEV_MODE = Boolean.getBoolean("dev-mode");
    private static final boolean NO_SUBSCRIPTION_SYNC = Boolean.getBoolean("no-sub-sync");


    public static boolean isDevMode() {
        return DEV_MODE;
    }

    public static boolean isNoSubscriptionSync() {
        return NO_SUBSCRIPTION_SYNC;
    }


}
