package org.theplaceholder.dmtv;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.theplaceholder.dmtv.client.config.VortexConfig;
import org.theplaceholder.dmtv.client.event.RightClickBlockEvent;

@Mod(DMTV.MODID)
public class DMTV {
    public static final String MODID = "timevortex";

    public DMTV() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> DMTV::registerClientConfigs);
    }

    private static void registerClientConfigs() {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        VortexConfig.registerClientConfig(CLIENT_BUILDER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build(), MODID + ".toml");
    }
}
