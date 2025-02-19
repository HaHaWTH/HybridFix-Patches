package io.wdsj.hybridfix_patch.residence.listener;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import io.wdsj.hybridfix_patch.residence.HybridFixResidence;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class ResHookEntityChangeBlockListener implements Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onChangeBlock(EntityChangeBlockEvent event) {
        if (!HybridFixResidence.config().patch_entity_block_change) return;
        Residence plugin = Residence.getInstance();
        if (plugin == null) return;
        // disabling event on world
        if (plugin.isDisabledWorldListener(event.getBlock().getWorld())) return;
        FlagPermissions perms = plugin.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.destroy, true)) {
            event.setCancelled(true);
        }
    }
}
