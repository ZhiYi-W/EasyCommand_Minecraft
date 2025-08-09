package cn.zy2gc.easycommand.Command.sethome;

import cn.zy2gc.easycommand.Command.sethome.Dao.PlayerHomePosDao;
import cn.zy2gc.easycommand.Command.sethome.entity.Pos;
import cn.zy2gc.easycommand.Command.sethome.until.PlayerSuggestionProvider;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Home {

    public static void register() {

        //sethome 命令
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("sethome")
                    .then(argument("homeName", StringArgumentType.string())
                            .executes(context -> {
                                // 获取命令源。这总是生效。
                                final ServerCommandSource source = context.getSource();
                                final MinecraftServer server = source.getServer();
                                ServerPlayerEntity player = source.getPlayer();
                                //家的名称
                                String homeName = StringArgumentType.getString(context, "homeName");

                                PlayerHomePosDao playerHomePosDao = new PlayerHomePosDao(player.getName().toString(), server);
                                ServerWorld world = player.getServerWorld();
                                String worldName = world.getRegistryKey().getValue().toString();
                                playerHomePosDao.putHome(homeName, (int) player.getX(), (int) player.getY(), (int) player.getZ(),worldName);
                                player.sendMessage(Text.literal("已设置家："+homeName).formatted(Formatting.GOLD));
                                return 1;
                            })));
        });


        //home 命令
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("home")
                    .then(argument("homeName", StringArgumentType.string())
                            .suggests(new PlayerSuggestionProvider())
                            .executes(context -> {
                                // 获取命令源。这总是生效。
                                final ServerCommandSource source = context.getSource();
                                final MinecraftServer server = source.getServer();
                                ServerPlayerEntity player = source.getPlayer();
                                //家的名称
                                String homeName = StringArgumentType.getString(context, "homeName");

                                PlayerHomePosDao playerHomePosDao = new PlayerHomePosDao(player.getName().toString(), server);
                                Pos pos = playerHomePosDao.getPos(homeName);
                                server.getCommandManager().executeWithPrefix(server.getCommandSource(), "execute as " + source.getName() + " in "+pos.getWorld()+ " run teleport " + pos.getX()+ " " + pos.getY()+ " " + pos.getZ());
                                player.sendMessage(Text.literal("已传送到家："+homeName).formatted(Formatting.GOLD));
                                return 1;
                            })));
        });

        ///homelist 命令
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("homelist")
                .executes(context -> {
                    // 获取命令源。这总是生效。
                    final ServerCommandSource source = context.getSource();
                    final MinecraftServer server = source.getServer();
                    PlayerHomePosDao playerHomePosDao = new PlayerHomePosDao(source.getPlayer().getName().toString(), server);

                    StringBuilder temp = new StringBuilder();
                    temp.append("家：");
                    for (String home : playerHomePosDao.getHomeNameList()){
                        temp.append(home).append(" , ");
                    }
                    String message = temp.substring(0,temp.length() - 3);
                    source.getPlayer().sendMessage(Text.literal(message).formatted(Formatting.GOLD));
                    return 1;
                })));


        //home 命令
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("delhome")
                    .then(argument("homeName", StringArgumentType.string())
                            .suggests(new PlayerSuggestionProvider())
                            .executes(context -> {
                                // 获取命令源。这总是生效。
                                final ServerCommandSource source = context.getSource();
                                final MinecraftServer server = source.getServer();
                                ServerPlayerEntity player = source.getPlayer();
                                //家的名称
                                String homeName = StringArgumentType.getString(context, "homeName");

                                PlayerHomePosDao playerHomePosDao = new PlayerHomePosDao(player.getName().toString(), server);
                                playerHomePosDao.delHome(homeName);
                                player.sendMessage(Text.literal("已删除家："+homeName).formatted(Formatting.GOLD));
                                return 1;
                            })));
        });



    }

}