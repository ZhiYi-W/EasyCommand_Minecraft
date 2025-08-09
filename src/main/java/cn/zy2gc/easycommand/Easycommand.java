package cn.zy2gc.easycommand;

import cn.zy2gc.easycommand.Command.Kill;
import cn.zy2gc.easycommand.Command.TPA;
import cn.zy2gc.easycommand.Command.sethome.Home;
import net.fabricmc.api.ModInitializer;

public class Easycommand implements ModInitializer {

    @Override
    public void onInitialize() {
        TPA.register();
        Kill.register();
        Home.register();
    }
}
