package me.ryanhamshire.GPFlags.flags;

import me.ryanhamshire.GPFlags.*;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FlagDef_ExitCommand extends PlayerMovementFlagDefinition
{
    @Override
    public boolean allowMovement(Player player, Location lastLocation, Location to)
    {
        if(lastLocation == null) return true;
        Flag flag = this.GetFlagInstanceAtLocation(lastLocation, player);
        if(flag == null) return true;
        
        if(flag == this.GetFlagInstanceAtLocation(to, player)) return true;

        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());
        String [] commandLines = flag.parameters.replace("%owner%", playerData.lastClaim.getOwnerName()).replace("%name%", player.getName()).replace("%uuid%", player.getUniqueId().toString()).split(";");
        for(String commandLine : commandLines)
        {
            GPFlags.addLogEntry("Exit command: " + commandLine);
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), commandLine);
        }

        return true;
    }
    
    public FlagDef_ExitCommand(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    public String getName()
    {
        return "ExitCommand";
    }

    @Override
    public SetFlagResult ValidateParameters(String parameters)
    {
        if(parameters.isEmpty())
        {
            return new SetFlagResult(false, new MessageSpecifier(Messages.ConsoleCommandRequired));
        }

        return new SetFlagResult(true, this.getSetMessage(parameters));
    }
    
    @Override
    public MessageSpecifier getSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.AddedExitCommand, parameters);
    }

    @Override
    public MessageSpecifier getUnSetMessage()
    {
        return new MessageSpecifier(Messages.RemovedExitCommand);
    }
}
