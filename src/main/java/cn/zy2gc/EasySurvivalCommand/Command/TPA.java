package cn.zy2gc.EasySurvivalCommand.Command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TPA {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("tpa")
                .then(argument("target", EntityArgumentType.player())
                        .executes(context -> {
                            // 获取命令源。这总是生效。
                            final ServerCommandSource source = context.getSource();
                            final MinecraftServer server = source.getServer();
                            ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
                            System.out.println("tp " + source.getName() + " " + target.getName());
                            server.getCommandManager().executeWithPrefix(server.getCommandSource(), "tp " + source.getName() + " " + target.getName().getString());
                            assert source.getPlayer() != null;
                            String playerName = target.getName().toString();
                            source.getPlayer().sendMessage(Text.literal("已传送到： " + playerName.substring(8,playerName.length()-1) + " 身边").formatted(Formatting.GOLD));
                            return 1;
                        }))));
    }
}