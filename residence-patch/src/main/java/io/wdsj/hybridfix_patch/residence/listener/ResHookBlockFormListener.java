package io.wdsj.hybridfix_patch.residence.listener;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import io.wdsj.hybridfix_patch.residence.HybridFixResidence;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.EntityBlockFormEvent;

public class ResHookBlockFormListener implements Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true) // Priority matters here, always higher than Residence
    public void onForm(BlockFormEvent event) {
        if (!HybridFixResidence.config().patch_block_form) return;
        if (!(event instanceof EntityBlockFormEvent)) return;
        if (!Flags.spread.isGlobalyEnabled()) return;
        Residence plugin = Residence.getInstance();
        if (plugin == null) return;
        if (plugin.isDisabledWorldListener(event.getBlock().getWorld())) return;
        FlagPermissions perms = plugin.getPermsByLoc(event.getBlock().getLocation());
        if (!perms.has(Flags.spread, true)) {
            event.setCancelled(true);
        }
    }
}
