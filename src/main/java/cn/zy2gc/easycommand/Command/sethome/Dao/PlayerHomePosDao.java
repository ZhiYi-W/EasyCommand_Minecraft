package cn.zy2gc.easycommand.Command.sethome.Dao;


import cn.zy2gc.easycommand.Command.sethome.entity.Pos;
import cn.zy2gc.easycommand.Command.sethome.persistent.StateSaverAndLoader;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraft.server.MinecraftServer;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerHomePosDao {

    private String playerName = "";
    private MinecraftServer server;

    private StateSaverAndLoader serverState;

    private Gson gson = new Gson();

    private Type type = new TypeToken<Map<String, Pos>>(){}.getType();

    public PlayerHomePosDao(String playerName,MinecraftServer server){
        serverState = StateSaverAndLoader.getServerState(server,playerName);
    }

    public void putHome(String homeName, int x, int y, int z, String world){
        Map<String,Pos> homePosMap = gson.fromJson(serverState.HomeMapString, type);
        homePosMap.put(homeName,new Pos(x, y, z,world));
        serverState.HomeMapString = gson.toJson(homePosMap);
    }

    public void delHome(String homeName){
        Map<String,Pos> homePosMap = gson.fromJson(serverState.HomeMapString, type);
        homePosMap.remove(homeName);
        serverState.HomeMapString = gson.toJson(homePosMap);
    }

    public Pos getPos(String homeName){
        Map<String,Pos> homePosMap = gson.fromJson(serverState.HomeMapString, type);
        return homePosMap.get(homeName);
    }

    public List<String> getHomeNameList(){
        Map<String,Pos> homePosMap = gson.fromJson(serverState.HomeMapString, type);
        Set<String> keys = homePosMap.keySet();
        return new LinkedList<>(keys);
    }


}
