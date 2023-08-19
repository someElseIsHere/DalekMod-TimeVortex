package org.theplaceholder.dmsm.client.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.theplaceholder.dmsm.client.vortex.Vortex;
import org.theplaceholder.dmsm.client.vortex.VortexType;

import java.util.Random;

public class VortexConfig {
    public static ForgeConfigSpec.EnumValue<VortexType> VORTEX_TYPE;
    public static ForgeConfigSpec.BooleanValue WHITE_HOLE;
    public static void registerClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Vortex Config").push("vortex");
        WHITE_HOLE = builder.comment("White Hole thingy").define("white_hole", false);
        VORTEX_TYPE = builder.comment("The type of vortex to use").defineEnum("vortex_type", VortexType.NONE);
        builder.pop();
    }

    public static Vortex getVortex() {
        if (VORTEX_TYPE.get() == VortexType.NONE) {
            int i = new Random().nextInt(VortexType.values().length);
            return VortexType.values()[i].vortex;
        }
        return VORTEX_TYPE.get().vortex;
    }

    public static boolean whiteHole() {
        return WHITE_HOLE.get();
    }
}
