package gtPlusPlus.xmod.gregtech.common.blocks.textures;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IIconContainer;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.Utils;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class TexturesGtBlocks {

	/*
	 * Handles Custom Textures.
	 */
	
	public static class CustomIcon implements IIconContainer, Runnable {
		protected IIcon mIcon;
		protected String mIconName;

		public CustomIcon(String aIconName) {
			mIconName = aIconName;
			Utils.LOG_WARNING("Constructing a Custom Texture. " + mIconName);
			GregTech_API.sGTBlockIconload.add(this);
		}

		@Override
		public IIcon getIcon() {
			return mIcon;
		}

		@Override
		public IIcon getOverlayIcon() {
			return null;
		}

		@Override
		public void run() {
			mIcon = GregTech_API.sBlockIcons.registerIcon(CORE.MODID + ":"  + mIconName);
			Utils.LOG_WARNING("FIND ME _ Processing texture: "+this.getTextureFile().getResourcePath());
		}

		@Override
		public ResourceLocation getTextureFile() {
			return TextureMap.locationBlocksTexture;
		}
	}
	
	
	/*
	 * Add Some Custom Textures below.
	 * I am not sure whether or not I need to declare them as such, but better to be safe than sorry.
	 * Right? 
	 */



	//Machine Casings
	//Simple
	private static final CustomIcon Internal_Casing_Machine_Simple_Top = new CustomIcon("TileEntities/machine_top");	
	public static final CustomIcon Casing_Machine_Simple_Top = Internal_Casing_Machine_Simple_Top;
	private static final CustomIcon Internal_Casing_Machine_Simple_Bottom = new CustomIcon("TileEntities/machine_bottom");	
	public static final CustomIcon Casing_Machine_Simple_Bottom = Internal_Casing_Machine_Simple_Bottom;
	//Advanced and Ultra
	private static final CustomIcon Internal_Casing_Machine_Advanced = new CustomIcon("TileEntities/high_adv_machine");	
	public static final CustomIcon Casing_Machine_Advanced = Internal_Casing_Machine_Advanced;
	private static final CustomIcon Internal_Casing_Machine_Ultra = new CustomIcon("TileEntities/adv_machine_lesu");	
	public static final CustomIcon Casing_Machine_Ultra = Internal_Casing_Machine_Ultra;
	//Dimensional - Non Overlay
	private static final CustomIcon Internal_Casing_Machine_Dimensional = new CustomIcon("TileEntities/adv_machine_dimensional");	
	public static final CustomIcon Casing_Machine_Dimensional = Internal_Casing_Machine_Dimensional;
	private static final CustomIcon Internal_Casing_Machine_Dimensional_Adv = new CustomIcon("TileEntities/high_adv_machine_dimensional");	
	public static final CustomIcon Casing_Machine_Dimensional_Adv = Internal_Casing_Machine_Dimensional_Adv;
	
	//Material Casings
	private static final CustomIcon Internal_Casing_Staballoy61 = new CustomIcon("TileEntities/MACHINE_CASING_STABLE_TANTALLOY61");	
	public static final CustomIcon Casing_Material_Staballoy61 = Internal_Casing_Staballoy61;
	private static final CustomIcon Internal_Casing_MaragingSteel = new CustomIcon("TileEntities/MACHINE_CASING_STABLE_MARAGINGSTEEL");	
	public static final CustomIcon Casing_Material_MaragingSteel = Internal_Casing_MaragingSteel;
	private static final CustomIcon Internal_Casing_Stellite = new CustomIcon("TileEntities/MACHINE_CASING_STABLE_STELLITE");	
	public static final CustomIcon Casing_Material_Stellite = Internal_Casing_Stellite;
	private static final CustomIcon Internal_Casing_Talonite = new CustomIcon("TileEntities/MACHINE_CASING_STABLE_TALONITE");	
	public static final CustomIcon Casing_Material_Talonite = Internal_Casing_Talonite;
	private static final CustomIcon Internal_Casing_Tumbaga = new CustomIcon("TileEntities/MACHINE_CASING_STABLE_TUMBAGA");	
	public static final CustomIcon Casing_Material_Tumbaga = Internal_Casing_Tumbaga;
	private static final CustomIcon Internal_Casing_Zeron100 = new CustomIcon("TileEntities/MACHINE_CASING_STABLE_ZERON100");	
	public static final CustomIcon Casing_Material_Zeron100 = Internal_Casing_Zeron100;
	private static final CustomIcon Internal_Casing_Potin = new CustomIcon("TileEntities/MACHINE_CASING_STABLE_POTIN");	
	public static final CustomIcon Casing_Material_Potin = Internal_Casing_Potin;
	//Misc Casings	
	private static final CustomIcon Internal_Casing_Machine_Redstone_Off = new CustomIcon("TileEntities/cover_redstone_conductor");	
	public static final CustomIcon Casing_Machine_Redstone_Off = Internal_Casing_Machine_Redstone_Off;
	private static final CustomIcon Internal_Casing_Machine_Redstone_On = new CustomIcon("TileEntities/cover_redstone_emitter");	
	public static final CustomIcon Casing_Machine_Redstone_On = Internal_Casing_Machine_Redstone_On;
	
	//Overlays
	//Fan Textures
	private static final CustomIcon Internal_Overlay_Machine_Vent = new CustomIcon("TileEntities/machine_top_vent_rotating");	
	public static final CustomIcon Overlay_Machine_Vent = Internal_Overlay_Machine_Vent;
	private static final CustomIcon Internal_Overlay_Machine_Vent_Fast = new CustomIcon("TileEntities/machine_top_vent_rotating_fast");	
	public static final CustomIcon Overlay_Machine_Vent_Fast = Internal_Overlay_Machine_Vent_Fast;
	private static final CustomIcon Internal_Overlay_Machine_Vent_Adv = new CustomIcon("TileEntities/adv_machine_vent_rotating");	
	public static final CustomIcon Overlay_Machine_Vent_Adv = Internal_Overlay_Machine_Vent_Adv;
	//Speaker Texture
	private static final CustomIcon Internal_Overlay_Machine_Sound = new CustomIcon("TileEntities/audio_out");	
	public static final CustomIcon Overlay_Machine_Sound = Internal_Overlay_Machine_Sound;
	private static final CustomIcon Internal_Overlay_Machine_Sound_Active = new CustomIcon("TileEntities/audio_out_active");	
	public static final CustomIcon Overlay_Machine_Sound_Active = Internal_Overlay_Machine_Sound_Active;
	//Diesel Engines
	private static final CustomIcon Internal_Overlay_Machine_Diesel_Vertical = new CustomIcon("TileEntities/machine_top_dieselmotor");	
	public static final CustomIcon Overlay_Machine_Diesel_Vertical = Internal_Overlay_Machine_Diesel_Vertical;
	private static final CustomIcon Internal_Overlay_Machine_Diesel_Horizontal = new CustomIcon("TileEntities/machine_top_dieselmotor2");	
	public static final CustomIcon Overlay_Machine_Diesel_Horizontal = Internal_Overlay_Machine_Diesel_Horizontal;
	private static final CustomIcon Internal_Overlay_Machine_Diesel_Vertical_Active = new CustomIcon("TileEntities/machine_top_dieselmotor_active");	
	public static final CustomIcon Overlay_Machine_Diesel_Vertical_Active = Internal_Overlay_Machine_Diesel_Vertical_Active;
	private static final CustomIcon Internal_Overlay_Machine_Diesel_Horizontal_Active = new CustomIcon("TileEntities/machine_top_dieselmotor2_active");	
	public static final CustomIcon Overlay_Machine_Diesel_Horizontal_Active = Internal_Overlay_Machine_Diesel_Horizontal_Active;
	//Computer Screens
	private static final CustomIcon Internal_Casing_Machine_Screen_1 = new CustomIcon("TileEntities/adv_machine_screen_random1");	
	public static final CustomIcon Casing_Machine_Screen_1 = Internal_Casing_Machine_Screen_1;
	private static final CustomIcon Internal_Casing_Machine_Screen_2 = new CustomIcon("TileEntities/adv_machine_screen_random2");	
	public static final CustomIcon Casing_Machine_Screen_2 = Internal_Casing_Machine_Screen_2;
	private static final CustomIcon Internal_Casing_Machine_Screen_3 = new CustomIcon("TileEntities/adv_machine_screen_random3");	
	public static final CustomIcon Casing_Machine_Screen_3 = Internal_Casing_Machine_Screen_3;
	private static final CustomIcon Internal_Casing_Machine_Screen_Frequency = new CustomIcon("TileEntities/adv_machine_screen_frequency");	
	public static final CustomIcon Casing_Machine_Screen_Frequency = Internal_Casing_Machine_Screen_Frequency;
	private static final CustomIcon Internal_Overlay_Machine_Screen_Logo = new CustomIcon("TileEntities/adv_machine_screen_logo");	
	public static final CustomIcon Overlay_Machine_Screen_Logo = Internal_Overlay_Machine_Screen_Logo;
	//Crafting Overlays
	private static final CustomIcon Internal_Overlay_Crafting_Bronze = new CustomIcon("TileEntities/bronze_top_crafting");	
	public static final CustomIcon Overlay_Crafting_Bronze = Internal_Overlay_Crafting_Bronze;
	private static final CustomIcon Internal_Overlay_Crafting_Steel = new CustomIcon("TileEntities/cover_crafting");	
	public static final CustomIcon Overlay_Crafting_Steel = Internal_Overlay_Crafting_Steel;
	//Dimensional
	private static final CustomIcon Internal_Overlay_Machine_Dimensional_Blue = new CustomIcon("TileEntities/adv_machine_dimensional_cover_blue");	
	public static final CustomIcon Overlay_Machine_Dimensional_Blue = Internal_Overlay_Machine_Dimensional_Blue;
	private static final CustomIcon Internal_Overlay_Machine_Dimensional_Orange = new CustomIcon("TileEntities/adv_machine_dimensional_cover_orange");	
	public static final CustomIcon Overlay_Machine_Dimensional_Orange = Internal_Overlay_Machine_Dimensional_Orange;
	//Icons
	private static final CustomIcon Internal_Overlay_MatterFab = new CustomIcon("TileEntities/adv_machine_matterfab");	
	public static final CustomIcon Overlay_MatterFab = Internal_Overlay_MatterFab;
	private static final CustomIcon Internal_Overlay_MatterFab_Active = new CustomIcon("TileEntities/adv_machine_matterfab_active");	
	public static final CustomIcon Overlay_MatterFab_Active = Internal_Overlay_MatterFab_Active;
	private static final CustomIcon Internal_Overlay_Oil = new CustomIcon("TileEntities/adv_machine_oil");	
	public static final CustomIcon Overlay_Oil = Internal_Overlay_Oil;
	private static final CustomIcon Internal_Overlay_UU_Matter = new CustomIcon("TileEntities/adv_machine_uum");	
	public static final CustomIcon Overlay_UU_Matter = Internal_Overlay_UU_Matter;

}
