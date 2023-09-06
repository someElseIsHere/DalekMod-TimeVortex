package org.theplaceholder.dmtv.client.event;

import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ClientTardisFlightCache;
import com.swdteam.common.block.tardis.FlightPanelBlock;
import com.swdteam.common.tardis.TardisData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.theplaceholder.dmtv.DMTV;
import org.theplaceholder.dmtv.client.vortex.VortexScreen;

@Mod.EventBusSubscriber(modid = DMTV.MODID, value = Dist.CLIENT)
public class RightClickBlockEvent {
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();

        if (!(event.getSide() == LogicalSide.CLIENT && world.getBlockState(pos).getBlock() instanceof FlightPanelBlock))
            return;
        if (!(ClientTardisFlightCache.hasTardisFlightData(pos) && ClientTardisFlightCache.getTardisFlightData(pos).hasEnteredFlight()))
            return;

        event.setCanceled(true);
        Minecraft.getInstance().setScreen(new VortexScreen());
    }
}
