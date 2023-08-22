package org.theplaceholder.dmtv.mixins;

import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.block.tardis.FlightPanelBlock;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.theplaceholder.dmtv.client.vortex.VortexScreen;

@Mixin(value = FlightPanelBlock.class, remap = false)
public class FlightPanelBlockMixin {

    @Inject(method = "func_225533_a_", at = @At("TAIL"))
    private void onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_, CallbackInfoReturnable<ActionResultType> cir) {
        if (!worldIn.isClientSide) return;

        if (ClientTardisCache.hasTardisData(pos)){
            TardisData data =  ClientTardisCache.getTardisData(pos);
            if (data.isInFlight()){
                Minecraft.getInstance().setScreen(new VortexScreen());
            }
        }
    }

    @Redirect(method = "func_225533_a_", at = @At(value = "INVOKE", target = "Lcom/swdteam/util/ChatUtil;sendError(Lnet/minecraft/entity/player/PlayerEntity;Ljava/lang/String;Lcom/swdteam/util/ChatUtil$MessageType;)V"), remap = false)
    private void onUseRedirect(PlayerEntity player, String s, ChatUtil.MessageType type) {}
}
