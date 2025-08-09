package cn.zy2gc.easycommand.Command.sethome.until;

import cn.zy2gc.easycommand.Command.sethome.Dao.PlayerHomePosDao;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.concurrent.CompletableFuture;

public class PlayerSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        // Thankfully, the ServerCommandSource has a method to get a list of player names.
        PlayerHomePosDao playerHomePosDao = new PlayerHomePosDao(source.getPlayer().getName().toString(), source.getServer());

        // Add all player names to the builder.
        for (String homeName : playerHomePosDao.getHomeNameList()) {
            builder.suggest(homeName);
        }

        // Lock the suggestions after we've modified them.
        return builder.buildFuture();
    }
}