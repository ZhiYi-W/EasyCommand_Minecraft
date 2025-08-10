package cn.zy2gc.EasySurvivalCommand;

import cn.zy2gc.EasySurvivalCommand.Command.Kill;
import cn.zy2gc.EasySurvivalCommand.Command.TPA;
import cn.zy2gc.EasySurvivalCommand.Command.sethome.Home;
import net.fabricmc.api.ModInitializer;

public class EasySurvivalCommand implements ModInitializer {

    @Override
    public void onInitialize() {
        TPA.register();
        Kill.register();
        Home.register();
    }
}
