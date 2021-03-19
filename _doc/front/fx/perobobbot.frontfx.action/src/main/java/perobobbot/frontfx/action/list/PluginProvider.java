package perobobbot.frontfx.action.list;

import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;

public class PluginProvider {

    public static Plugin provider() {
        return Plugin.with(PluginType.FRONT_FX,"Action List",PluginProvider.class);
    }

}
