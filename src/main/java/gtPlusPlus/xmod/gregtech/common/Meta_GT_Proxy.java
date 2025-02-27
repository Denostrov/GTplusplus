package gtPlusPlus.xmod.gregtech.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TAE;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.util.GTPP_Recipe;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.data.AutoMap;
import gtPlusPlus.api.objects.minecraft.FormattedTooltipString;
import gtPlusPlus.core.handler.AchievementHandler;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.material.ELEMENT;
import gtPlusPlus.core.util.Utils;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.LangUtils;
import gtPlusPlus.core.util.minecraft.MaterialUtils;
import gtPlusPlus.core.util.minecraft.gregtech.PollutionUtils;
import gtPlusPlus.xmod.gregtech.api.metatileentity.BaseCustomTileEntity;
import gtPlusPlus.xmod.gregtech.api.metatileentity.custom.power.BaseCustomPower_MTE;
import gtPlusPlus.xmod.gregtech.common.covers.CoverManager;
import gtPlusPlus.xmod.gregtech.common.helpers.MachineUpdateHandler;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;

public class Meta_GT_Proxy {

    public static List<Runnable> GT_BlockIconload = new ArrayList<>();
    public static List<Runnable> GT_ItemIconload = new ArrayList<>();

    public static AutoMap<Integer> GT_ValidHeatingCoilMetas = new AutoMap<Integer>();

    private static Class<BaseCustomTileEntity> sBaseMetaTileEntityClass;
    private static Class<BaseCustomPower_MTE> sBaseMetaTileEntityClass2;

    public static AchievementHandler mAssemblyAchievements;

    public static final Map<String, FormattedTooltipString> mCustomGregtechMetaTooltips = new LinkedHashMap<String, FormattedTooltipString>();

    @SideOnly(Side.CLIENT)
    public static IIconRegister sBlockIcons, sItemIcons;

    public Meta_GT_Proxy() {}

    public static Block sBlockMachines;

    public static void preInit() {

        // New GT++ Block, yay! (Progress)
        // sBlockMachines = new GTPP_Block_Machines();
        fixIC2FluidNames();

        GT_Log.out.println("GT++ Mod: Register TileEntities.");
        BaseMetaTileEntity tBaseMetaTileEntity = constructBaseMetaTileEntity();
        BaseMetaTileEntity tBaseMetaTileEntity2 = constructBaseMetaTileEntityCustomPower();

        GT_Log.out.println("GT++ Mod: Testing BaseMetaTileEntity.");
        if (tBaseMetaTileEntity == null || tBaseMetaTileEntity2 == null) {
            GT_Log.err.println(
                    "GT++ Mod: Fatal Error ocurred while initializing custom BaseMetaTileEntities, crashing Minecraft.");
            CORE.crash(
                    "GT++ Mod: Fatal Error ocurred while initializing custom BaseMetaTileEntities, crashing Minecraft.");
        }

        GT_Log.out.println("GT++ Mod: Registering custom BaseMetaTileEntities.");
        GameRegistry.registerTileEntity(tBaseMetaTileEntity.getClass(), "BaseMetaTileEntity_GTPP");
        GameRegistry.registerTileEntity(tBaseMetaTileEntity2.getClass(), "BaseMetaTileEntity_GTPP2");
        CoverManager.generateCustomCovers();
    }

    public static void init() {
        setValidHeatingCoilMetas();
        PollutionUtils.setPollutionFluids();
        fixIC2FluidNames();
        Utils.registerEvent(new MachineUpdateHandler());
    }

    public static void postInit() {
        mAssemblyAchievements = new AchievementHandler();
        fixIC2FluidNames();

        // Finalise TAE
        TAE.finalizeTAE();
    }

    @SuppressWarnings("deprecation")
    public static void fixIC2FluidNames() {
        // Fix IC2 Hot Water name
        try {
            String aNewHeatedWaterName = "Heated Water";
            Logger.INFO("Renaming [IC2 Hotspring Water] --> [" + aNewHeatedWaterName + "].");
            LanguageRegistry.instance().addStringLocalization("fluidHotWater", "Heated Water");
            LanguageRegistry.instance().addStringLocalization("fluidHotWater", aNewHeatedWaterName);
            LanguageRegistry.instance().addStringLocalization("ic2.fluidHotWater", aNewHeatedWaterName);
            GT_LanguageManager.addStringLocalization("fluidHotWater", aNewHeatedWaterName);
            GT_LanguageManager.addStringLocalization("ic2.fluidHotWater", aNewHeatedWaterName);

            Block b = BlocksItems.getFluidBlock(InternalName.fluidHotWater);
            if (b != null) {
                LanguageRegistry.addName(ItemUtils.getSimpleStack(b), aNewHeatedWaterName);
                LanguageRegistry.instance().addStringLocalization(b.getUnlocalizedName(), aNewHeatedWaterName);
                GT_LanguageManager.addStringLocalization(b.getUnlocalizedName(), aNewHeatedWaterName);
            }
            Fluid f = BlocksItems.getFluid(InternalName.fluidHotWater);
            if (f != null) {
                LanguageRegistry.instance().addStringLocalization(f.getUnlocalizedName(), aNewHeatedWaterName);
                GT_LanguageManager.addStringLocalization(f.getUnlocalizedName(), aNewHeatedWaterName);
                int aDam = FluidRegistry.getFluidID(f);
                ItemStack s = ItemList.Display_Fluid.getWithDamage(1, aDam);
                if (s != null) {
                    LanguageRegistry.addName(s, aNewHeatedWaterName);
                }
            }

            String[] aLangs = new String[] { "de_DE", "en_US", "en_GB", "en_IC", "es_AR", "es_ES", "es_MX", "es_UY",
                    "es_VE", "fr_CA", "fr_FR", "it_IT", "ko_KR", "pt_BR", "pt_PT", "ru_RU", "sv_SE", "tr_TR", "zh_CN",
                    "zh_TW", };
            String[] aLangValues = new String[] { "Erhitztes Wasser", "Heated Water", "Heated Water", "Heated Water",
                    "Agua caliente", "Agua caliente", "Agua caliente", "Agua caliente", "Agua caliente", "Eau chauffée",
                    "Eau chauffée", "Acqua riscaldata", "온수", "Água aquecida", "Água aquecida", "Вода с подогревом",
                    "Uppvärmt vatten", "Isıtılmış Su", "热水", "热水", };
            for (int i = 0; i < aLangs.length; i++) {
                Logger.REFLECTION(
                        "Trying to inject new lang data for " + aLangs[i] + ", using value: " + aLangValues[i]);
                LangUtils.rewriteEntryForLanguageRegistry(aLangs[i], "fluidHotWater", aLangValues[i]);
                LangUtils.rewriteEntryForLanguageRegistry(aLangs[i], "ic2.fluidHotWater", aLangValues[i]);
            }
        } catch (Throwable t) {

        }
    }

    public static boolean generatePlasmaRecipesForAdvVacFreezer() {

        AutoMap<GT_Recipe> aFreezerMapRebaked = new AutoMap<GT_Recipe>();
        AutoMap<GT_Recipe> aRemovedRecipes = new AutoMap<GT_Recipe>();

        // Find recipes containing Plasma and map them
        for (GT_Recipe y : GTPP_Recipe.GTPP_Recipe_Map.sAdvFreezerRecipes_GT.mRecipeList) {
            if (y.mFluidInputs.length > 0) {
                for (FluidStack r : y.mFluidInputs) {
                    if (r.getUnlocalizedName().toLowerCase().contains("plasma")) {
                        aRemovedRecipes.put(y);
                        continue;
                    }
                }
                aFreezerMapRebaked.put(y);
            }
        }

        AutoMap<GTPP_Recipe> aNewRecipes = new AutoMap<GTPP_Recipe>();
        int aAtomicMass = 0;
        int aAtomicTier = 0;

        final FluidStack NULL_PLASMA = Materials._NULL.getPlasma(1);

        for (String s : ELEMENT.NAMES) {

            aAtomicMass++;
            aAtomicTier = (aAtomicMass / 30) + 1;
            FluidStack aMoltenFluid = null;
            FluidStack aPlasma = null;

            // Try Get Material via Gregtech
            Materials aGregMaterial = MaterialUtils.getMaterial(s);
            if (aGregMaterial != null) {
                aMoltenFluid = aGregMaterial.getMolten(1);
                if (aMoltenFluid == null) {
                    aMoltenFluid = aGregMaterial.getFluid(1);
                    if (aMoltenFluid == null) {
                        aMoltenFluid = aGregMaterial.getGas(1);
                        if (aMoltenFluid == null) {
                            aMoltenFluid = aGregMaterial.getSolid(1);
                        }
                    }
                }
                aPlasma = aGregMaterial.getPlasma(100);
            }

            // Just wildcard values
            if (aMoltenFluid == null || aPlasma == null) {
                if (aMoltenFluid == null) {
                    aMoltenFluid = FluidUtils.getWildcardFluidStack(s, 1);
                }
                if (aPlasma == null) {
                    aPlasma = FluidUtils.getFluidStack("plasma." + s.toLowerCase(), 1);
                }
            }

            // Skip this material
            if (aMoltenFluid == null || aPlasma == null || aPlasma.isFluidEqual(NULL_PLASMA)) {
                Logger.INFO(
                        "Could not generate Advanced Vacuum Freezer recipe. Cooling " + s
                                + " plasma. Molten Form Exists? "
                                + (aMoltenFluid != null)
                                + " | Plasma Exists? "
                                + (aPlasma != null));
                continue;
            } else {
                // Build a new plasma recipe
                int aTotalTickTime = (20 * 1 + (aAtomicMass));
                GTPP_Recipe aTempRecipe = new GTPP_Recipe(
                        true,
                        new ItemStack[] {},
                        new ItemStack[] {},
                        null,
                        new int[] { 10000 },
                        new FluidStack[] { aPlasma, FluidUtils.getFluidStack("cryotheum", aTotalTickTime) },
                        new FluidStack[] { aMoltenFluid },
                        aTotalTickTime,
                        (int) GT_Values.V[4 + aAtomicTier],
                        aAtomicMass);

                // Add it to the map if it's valid
                if (aTempRecipe != null) {
                    aNewRecipes.put(aTempRecipe);
                }
            }
        }

        // Add the new recipes to the map we will rebake over the original
        for (GTPP_Recipe w : aNewRecipes) {
            aFreezerMapRebaked.put(w);
        }

        // Best not touch the original map if we don't have a valid map to override it with.
        if (aFreezerMapRebaked.size() > 0) {

            int aOriginalCount = GTPP_Recipe.GTPP_Recipe_Map.sAdvFreezerRecipes_GT.mRecipeList.size();

            // Empty the original map
            GTPP_Recipe.GTPP_Recipe_Map.sAdvFreezerRecipes_GT.mRecipeList.clear();

            // Rebake the real map
            for (GT_Recipe w : aFreezerMapRebaked) {
                GTPP_Recipe.GTPP_Recipe_Map.sAdvFreezerRecipes_GT.mRecipeList.add(w);
            }

            return GTPP_Recipe.GTPP_Recipe_Map.sAdvFreezerRecipes_GT.mRecipeList.size() >= aOriginalCount;
        }

        return false;
    }

    public static TileEntity constructCustomGregtechMetaTileEntityByMeta(int aMeta) {
        if (aMeta == 12) {
            return Meta_GT_Proxy.constructBaseMetaTileEntityCustomPower();
        } else {
            return Meta_GT_Proxy.constructBaseMetaTileEntity();
        }
    }

    public static BaseCustomTileEntity constructBaseMetaTileEntity() {
        if (sBaseMetaTileEntityClass == null) {
            try {
                sBaseMetaTileEntityClass = BaseCustomTileEntity.class;
                return (BaseCustomTileEntity) BaseCustomTileEntity.class.newInstance();
            } catch (Throwable arg1) {
                try {
                    Constructor<?> g = BaseCustomTileEntity.class.getConstructors()[0];
                    g.setAccessible(true);
                    return (BaseCustomTileEntity) g.newInstance();
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | SecurityException e) {}
            }
        }
        try {
            return (BaseCustomTileEntity) ((BaseCustomTileEntity) sBaseMetaTileEntityClass.newInstance());
        } catch (Throwable arg0) {
            arg0.printStackTrace(GT_Log.err);
            try {
                Constructor<?> g = BaseCustomTileEntity.class.getConstructors()[0];
                g.setAccessible(true);
                return (BaseCustomTileEntity) g.newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | SecurityException e) {
                GT_Log.err
                        .println("GT++ Mod: Fatal Error ocurred while initializing TileEntities, crashing Minecraft.");
                e.printStackTrace(GT_Log.err);
                CORE.crash(
                        "GT++ Mod: Fatal Error ocurred while initializing custom BaseMetaTileEntities, crashing Minecraft.");
                throw new RuntimeException(e);
            }
        }
    }

    public static BaseCustomPower_MTE constructBaseMetaTileEntityCustomPower() {
        if (sBaseMetaTileEntityClass2 == null) {
            try {
                sBaseMetaTileEntityClass2 = BaseCustomPower_MTE.class;
                return (BaseCustomPower_MTE) BaseCustomPower_MTE.class.newInstance();
            } catch (Throwable arg1) {
                try {
                    Constructor<?> g = BaseCustomPower_MTE.class.getConstructors()[0];
                    g.setAccessible(true);
                    return (BaseCustomPower_MTE) g.newInstance();
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | SecurityException e) {
                    // e.printStackTrace();
                }
            }
        }

        try {
            return (BaseCustomPower_MTE) ((BaseCustomPower_MTE) sBaseMetaTileEntityClass2.newInstance());
        } catch (Throwable arg0) {
            arg0.printStackTrace(GT_Log.err);
            try {
                Constructor<?> g = BaseCustomPower_MTE.class.getConstructors()[0];
                g.setAccessible(true);
                return (BaseCustomPower_MTE) g.newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | SecurityException e) {
                GT_Log.err
                        .println("GT++ Mod: Fatal Error ocurred while initializing TileEntities, crashing Minecraft.");
                e.printStackTrace(GT_Log.err);
                CORE.crash(
                        "GT++ Mod: Fatal Error ocurred while initializing custom BaseMetaTileEntities, crashing Minecraft.");
                throw new RuntimeException(e);
            }
        }
    }

    public static void setValidHeatingCoilMetas() {
        for (int i = 0; i <= 6; i++) {
            GT_ValidHeatingCoilMetas.put(i);
        }
        for (int i = 7; i <= 8; i++) {
            GT_ValidHeatingCoilMetas.put(i);
        }
    }

    static GT_Proxy[] mProxies = new GT_Proxy[2];

    public static void setCustomGregtechTooltip(String aNbtTagName, FormattedTooltipString aData) {
        mCustomGregtechMetaTooltips.put(aNbtTagName, aData);
    }

    public static void conStructGtTileBlockTooltip(ItemStack aStack, EntityPlayer aPlayer, List<Object> aList,
            boolean par4) {
        try {
            int tDamage = aStack.getItemDamage();
            if ((tDamage <= 0) || (tDamage >= GregTech_API.METATILEENTITIES.length)) {
                return;
            }

            if (GregTech_API.METATILEENTITIES[tDamage] != null) {
                IGregTechTileEntity tTileEntity = GregTech_API.METATILEENTITIES[tDamage].getBaseMetaTileEntity();
                if (tTileEntity.getDescription() != null) {
                    int i = 0;
                    for (String tDescription : tTileEntity.getDescription()) {
                        if (GT_Utility.isStringValid(tDescription)) {
                            if (tDescription.contains("%%%")) {
                                String[] tString = tDescription.split("%%%");
                                if (tString.length >= 2) {
                                    StringBuffer tBuffer = new StringBuffer();
                                    Object tRep[] = new String[tString.length / 2];
                                    for (int j = 0; j < tString.length; j++) if (j % 2 == 0) tBuffer.append(tString[j]);
                                    else {
                                        tBuffer.append(" %s");
                                        tRep[j / 2] = tString[j];
                                    }
                                    aList.add(
                                            String.format(
                                                    GT_LanguageManager.addStringLocalization(
                                                            "TileEntity_DESCRIPTION_" + tDamage + "_Index_" + i++,
                                                            tBuffer.toString(),
                                                            !GregTech_API.sPostloadFinished),
                                                    tRep));
                                }
                            } else {
                                String tTranslated = GT_LanguageManager.addStringLocalization(
                                        "TileEntity_DESCRIPTION_" + tDamage + "_Index_" + i++,
                                        tDescription,
                                        !GregTech_API.sPostloadFinished);
                                aList.add(tTranslated.equals("") ? tDescription : tTranslated);
                            }
                        } else i++;
                    }
                }

                if (tTileEntity.getEUCapacity() > 0L) {

                    final long tVoltage = tTileEntity.getInputVoltage();
                    byte tTier = (byte) ((byte) Math.max(1, GT_Utility.getTier(tVoltage)));

                    // Custom handling
                    if (tDamage < 30500 && tDamage >= 30400) {
                        int aOffset = tDamage - 30400;
                        if ((aOffset) <= 10) {
                            tTier -= 2;
                            aList.add(EnumChatFormatting.BOLD + "16" + " Fuse Slots" + EnumChatFormatting.GRAY);
                            aList.add(
                                    "Per each fuse, you may insert " + EnumChatFormatting.YELLOW
                                            + (GT_Values.V[tTier])
                                            + EnumChatFormatting.GRAY
                                            + " EU/t");
                            aList.add(
                                    "However this " + EnumChatFormatting.ITALIC
                                            + EnumChatFormatting.RED
                                            + "MUST"
                                            + EnumChatFormatting.GRAY
                                            + " be in a single Amp");
                            aList.add(
                                    "This machine can accept upto a single amp of "
                                            + GT_Values.VN[Math.min(tTier + 2, 12)]
                                            + " as a result");
                            aList.add(
                                    GT_LanguageManager.addStringLocalization(
                                            "TileEntity_Breaker_Loss",
                                            "Breaker Loss: " + EnumChatFormatting.RED
                                                    + ""
                                                    + (GT_Values.V[Math.max(tTier - 1, 0)] / 10)
                                                    + EnumChatFormatting.GRAY
                                                    + " EU/t",
                                            !GregTech_API.sPostloadFinished) + EnumChatFormatting.GRAY);
                        }

                        aList.add(
                                GT_LanguageManager.addStringLocalization(
                                        "TileEntity_Special_Power_1",
                                        EnumChatFormatting.RED + "Special Power Handling, please read manual",
                                        !GregTech_API.sPostloadFinished) + EnumChatFormatting.GRAY);
                        // aList.add(GT_LanguageManager.addStringLocalization("TileEntity_BreakerBox_2",
                        // EnumChatFormatting.RED+"Special Power Handling, please read manual",
                        // !GregTech_API.sPostloadFinished) + EnumChatFormatting.GRAY);
                        // aList.add(GT_LanguageManager.addStringLocalization("TileEntity_BreakerBox_3",
                        // EnumChatFormatting.RED+"Special Power Handling, please read manual",
                        // !GregTech_API.sPostloadFinished) + EnumChatFormatting.GRAY);
                    }

                    if (tTileEntity.getInputVoltage() > 0L) {
                        String inA = "0";
                        if (tTileEntity.getInputAmperage() >= 1L) {
                            inA = " at " + EnumChatFormatting.YELLOW
                                    + tTileEntity.getInputAmperage()
                                    + EnumChatFormatting.GRAY
                                    + " Amps";
                        } else {
                            inA = " at " + EnumChatFormatting.WHITE
                                    + tTileEntity.getInputAmperage()
                                    + EnumChatFormatting.GRAY
                                    + " Amps";
                        }
                        String a1 = "Voltage IN: " + EnumChatFormatting.GREEN
                                + tTileEntity.getInputVoltage()
                                + " ("
                                + GT_Values.VN[GT_Utility.getTier(tTileEntity.getInputVoltage())]
                                + ")"
                                + EnumChatFormatting.GRAY
                                + inA;
                        aList.add(a1);
                    }

                    if (tTileEntity.getOutputVoltage() > 0L) {
                        String outA = "0";
                        if (tTileEntity.getOutputAmperage() >= 1L) {
                            outA = " at " + EnumChatFormatting.YELLOW
                                    + tTileEntity.getOutputAmperage()
                                    + EnumChatFormatting.GRAY
                                    + " Amps";
                        } else {
                            outA = " at " + EnumChatFormatting.WHITE
                                    + tTileEntity.getOutputAmperage()
                                    + EnumChatFormatting.GRAY
                                    + " Amps";
                        }
                        String a1 = "Voltage OUT: " + EnumChatFormatting.GREEN
                                + tTileEntity.getOutputVoltage()
                                + " ("
                                + GT_Values.VN[GT_Utility.getTier(tTileEntity.getOutputVoltage())]
                                + ")"
                                + EnumChatFormatting.GRAY
                                + outA;
                        aList.add(a1);
                    }

                    if (tTileEntity.getOutputVoltage() > 0L) {
                        aList.add(
                                GT_LanguageManager.addStringLocalization(
                                        "TileEntity_Lossess_EU",
                                        "Transmission Loss: " + EnumChatFormatting.DARK_BLUE
                                                + ""
                                                + (tDamage < 30500 && tDamage >= 30400 ? 0 : 1),
                                        !GregTech_API.sPostloadFinished) + EnumChatFormatting.GRAY);
                    }

                    if (tTileEntity.getEUCapacity() > 0) {
                        aList.add(
                                GT_LanguageManager.addStringLocalization(
                                        "TileEntity_EUp_STORE2",
                                        "Internal Capacity: ",
                                        !GregTech_API.sPostloadFinished) + EnumChatFormatting.BLUE
                                        + tTileEntity.getEUCapacity()
                                        + EnumChatFormatting.GRAY
                                        + " EU");
                    }
                }

                /*
                 * if (tTileEntity.getEUCapacity() > 0L) { if (tTileEntity.getInputVoltage() > 0L) {
                 * aList.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_IN", "Voltage IN: ",
                 * !GregTech_API.sPostloadFinished ) + EnumChatFormatting.GREEN + tTileEntity.getInputVoltage() + " (" +
                 * GT_Values.VN[GT_Utility.getTier(tTileEntity.getInputVoltage())] + ")" + EnumChatFormatting.GRAY); }
                 * if (tTileEntity.getOutputVoltage() > 0L) {
                 * aList.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_OUT", "Voltage OUT: ",
                 * !GregTech_API.sPostloadFinished ) + EnumChatFormatting.GREEN + tTileEntity.getOutputVoltage() + " ("
                 * + GT_Values.VN[GT_Utility.getTier(tTileEntity.getOutputVoltage())] + ")" + EnumChatFormatting.GRAY);
                 * } if (tTileEntity.getOutputAmperage() > 1L) {
                 * aList.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_AMOUNT", "Amperage: ",
                 * !GregTech_API.sPostloadFinished ) + EnumChatFormatting.YELLOW + tTileEntity.getOutputAmperage() +
                 * EnumChatFormatting.GRAY); }
                 * aList.add(GT_LanguageManager.addStringLocalization("TileEntity_EUp_STORE", "Capacity: ",
                 * !GregTech_API.sPostloadFinished ) + EnumChatFormatting.BLUE + tTileEntity.getEUCapacity() +
                 * EnumChatFormatting.GRAY); }
                 */

            }
            NBTTagCompound aNBT = aStack.getTagCompound();
            if (aNBT != null) {
                if (aNBT.getBoolean("mMuffler")) {
                    aList.add(
                            GT_LanguageManager.addStringLocalization(
                                    "GT_TileEntity_MUFFLER",
                                    "has Muffler Upgrade",
                                    !GregTech_API.sPostloadFinished));
                }
                if (aNBT.getBoolean("mSteamConverter")) {
                    aList.add(
                            GT_LanguageManager.addStringLocalization(
                                    "GT_TileEntity_STEAMCONVERTER",
                                    "has Steam Upgrade",
                                    !GregTech_API.sPostloadFinished));
                }
                int tAmount = 0;
                if ((tAmount = aNBT.getByte("mSteamTanks")) > 0) {
                    aList.add(
                            tAmount + " "
                                    + GT_LanguageManager.addStringLocalization(
                                            "GT_TileEntity_STEAMTANKS",
                                            "Steam Tank Upgrades",
                                            !GregTech_API.sPostloadFinished));
                }

                FluidStack afluid = net.minecraftforge.fluids.FluidStack
                        .loadFluidStackFromNBT(aNBT.getCompoundTag("mFluid"));
                if (afluid != null) {
                    int tFluidAmount = afluid.amount;
                    if (tFluidAmount > 0) {
                        aList.add(
                                GT_LanguageManager.addStringLocalization(
                                        "GT_TileEntity_FLUIDTANK",
                                        "Tank Fluid: " + tFluidAmount + "L " + afluid.getLocalizedName() + "",
                                        !GregTech_API.sPostloadFinished));
                    }
                }
            }

            // Add Custom Here

            // Add Custom Tooltips
            for (String s : mCustomGregtechMetaTooltips.keySet()) {
                if (aNBT.hasKey(s)) {
                    String aTip = mCustomGregtechMetaTooltips.get(s).getTooltip(aNBT.getString(s));
                    aList.add(aTip);
                }
            }

            // Add GT++ Stuff

            if (tDamage >= 30400 && tDamage < 30500) {
                aList.add(EnumChatFormatting.UNDERLINE + "Special GT++ Machine");
            }
            if ((tDamage >= 750 && tDamage < 1000) || (tDamage >= 30000 && tDamage < 31000)) {
                aList.add(CORE.GT_Tooltip);
            }

        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
        }
    }
}
