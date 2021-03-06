package me.ryanhamshire.GPFlags.flags;

import me.ryanhamshire.GPFlags.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFormEvent;

import java.util.Arrays;
import java.util.List;

public class FlagDef_NoIceForm extends FlagDefinition {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGrowth(BlockFormEvent event)
    {
        Block block = event.getBlock();

        Flag flag = this.GetFlagInstanceAtLocation(block.getLocation(), null);
        if(flag == null) return;

        if(event.getNewState().getType() != Material.ICE) return;
        event.setCancelled(true);
    }

    public FlagDef_NoIceForm(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }

    @Override
    public String getName()
    {
        return "NoIceForm";
    }

    @Override
	public MessageSpecifier getSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNoIceForm);
    }

    @Override
    public MessageSpecifier getUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNoIceForm);
    }

    @Override
    public List<FlagType> getFlagType() {
        return Arrays.asList(FlagType.CLAIM, FlagType.WORLD, FlagType.SERVER);
    }

}
