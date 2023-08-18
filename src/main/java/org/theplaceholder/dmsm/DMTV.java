package org.theplaceholder.dmsm;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.theplaceholder.dmsm.client.config.VortexConfig;
import org.theplaceholder.dmsm.client.vortex.VortexType;

@Mod(DMTV.MODID)
public class DMTV {
    public static final String MODID = "timevortex";

    public DMTV() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            VortexType.init();
            registerClientConfigs();
        });
    }

    private static void registerClientConfigs() {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        VortexConfig.registerClientConfig(CLIENT_BUILDER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
}
