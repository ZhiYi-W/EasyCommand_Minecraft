package cn.zy2gc.EasySurvivalCommand.Command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.literal;

public class KillMe {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("killme")
                .executes(context -> {
                    // 获取命令源。这总是生效。
                    final ServerCommandSource source = context.getSource();
                    final MinecraftServer server = source.getServer();
                    server.getCommandManager().executeWithPrefix(server.getCommandSource(), "kill "+source.getName());
                    assert source.getPlayer() != null;
                    source.getPlayer().sendMessage(Text.literal("你自杀了").formatted(Formatting.GOLD));
                    return 1;
                })));
    }
}
