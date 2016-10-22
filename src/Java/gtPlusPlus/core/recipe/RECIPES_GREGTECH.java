package gtPlusPlus.core.recipe;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gtPlusPlus.core.item.ModItems;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.lib.LoadedMods;
import gtPlusPlus.core.util.Utils;
import gtPlusPlus.core.util.fluid.FluidUtils;
import gtPlusPlus.core.util.item.UtilsItems;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RECIPES_GREGTECH {

	public static void run(){
		Utils.LOG_INFO("Loading Recipes through GregAPI for Industrial Multiblocks.");
		execute();
	}

	private static void execute(){
		cokeOvenRecipes();
		matterFabRecipes();
		assemblerRecipes();
		distilleryRecipes();
		extractorRecipes();
		chemicalBathRecipes();
		chemicalReactorRecipes();
		dehydratorRecipes();
		blastFurnaceRecipes();
		addFuels();
	}

	private static void cokeOvenRecipes(){
		Utils.LOG_INFO("Loading Recipes for Industrial Coking Oven.");

		try {

			//GT Logs to Charcoal Recipe	
			//With Sulfuric Acid
			CORE.RA.addCokeOvenRecipe(
					GT_OreDictUnificator.get(OrePrefixes.log, Materials.Wood, 1L), //Input 1
					GT_OreDictUnificator.get(OrePrefixes.log, Materials.Wood, 1L), //Input 2
					Materials.SulfuricAcid.getFluid(20L), //Fluid Input
					Materials.Creosote.getFluid(175L), //Fluid Output
					GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Charcoal, 2L), //Item Output 
					800,  //Time in ticks
					30); //EU
		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}
		try {

			//Coal -> Coke Recipe
			//With Sulfuric Acid
			CORE.RA.addCokeOvenRecipe(
					GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Coal, 1L), //Input 1
					GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Coal, 1L), //Input 2
					Materials.SulfuricAcid.getFluid(60L), //Fluid Input
					Materials.Creosote.getFluid(250L), //Fluid Output
					UtilsItems.getItemStack("Railcraft:fuel.coke", 2), //Item Output 
					600,  //Time in ticks
					120); //EU			
		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}

		try {
			//GT Logs to Charcoal Recipe	
			//Without Sulfuric Acid
			CORE.RA.addCokeOvenRecipe(
					GT_OreDictUnificator.get(OrePrefixes.log, Materials.Wood, 1L), //Input 1
					GT_OreDictUnificator.get(OrePrefixes.log, Materials.Wood, 1L), //Input 2
					FluidUtils.getFluidStack("oxygen", 80), //Fluid Input
					Materials.Creosote.getFluid(145L), //Fluid Output
					GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Charcoal, 2L), //Item Output 
					1200,  //Time in ticks
					30); //EU
		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}

		try {
			//Coal -> Coke Recipe
			//Without Sulfuric Acid
			CORE.RA.addCokeOvenRecipe(
					GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Coal, 1L), //Input 1
					GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Coal, 1L), //Input 2
					FluidUtils.getFluidStack("oxygen", 185), //Fluid Input
					Materials.Creosote.getFluid(200L), //Fluid Output
					UtilsItems.getItemStack("Railcraft:fuel.coke", 2), //Item Output 
					900,  //Time in ticks
					120); //EU
		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}
	}

	private static void matterFabRecipes(){
		Utils.LOG_INFO("Loading Recipes for Matter Fabricator.");

		try {

			CORE.RA.addMatterFabricatorRecipe(
					Materials.UUAmplifier.getFluid(1L), //Fluid Input
					Materials.UUMatter.getFluid(1L), //Fluid Output
					800,  //Time in ticks
					32); //EU
		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}
		try {

			CORE.RA.addMatterFabricatorRecipe(
					null, //Fluid Input
					Materials.UUMatter.getFluid(1L), //Fluid Output
					3200,  //Time in ticks
					32); //EU			
		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}

	}

	private static void dehydratorRecipes(){
		Utils.LOG_INFO("Loading Recipes for Chemical Dehydrator.");

		/*try {

			//Makes Lithium Carbonate
			CORE.RA.addDehydratorRecipe(
					FluidUtils.getFluidStack("sulfuriclithium", 1000), //Item input (slot 1)
					null, //Fluid Input
					new ItemStack[]{
						UtilsItems.getItemStackOfAmountFromOreDict("dustSodium", 1),
						UtilsItems.getItemStackOfAmountFromOreDict("dustCarbon", 1),
						UtilsItems.getItemStackOfAmountFromOreDict("dustLithium", 1)
						}, //Output Array of Items - Upto 9
					10*20, //Time in ticks
					30); //EU	
		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}*/
		try {

			ItemStack cells = UtilsItems.getItemStackWithMeta(LoadedMods.IndustrialCraft2, "IC2:itemCellEmpty", "Empty Fluid Cells", 0, 12);

			if (cells == null){
				cells = UtilsItems.getItemStackOfAmountFromOreDictNoBroken("cellEmpty", 12);
			}

			ItemStack[] input = {cells, UtilsItems.getItemStackOfAmountFromOreDict("dustLepidolite", 20)};

			CORE.RA.addDehydratorRecipe(
					input, //Item input (Array, up to 2)
					FluidUtils.getFluidStack("sulfuricacid", 10000), //Fluid input (slot 1)
					FluidUtils.getFluidStack("sulfuriclithium", 10000), //Fluid output (slot 2)
					new ItemStack[]{
						UtilsItems.getItemStackOfAmountFromOreDict("dustPotassium", 1),
						UtilsItems.getItemStackOfAmountFromOreDict("dustAluminium", 4),
						UtilsItems.getItemStackOfAmountFromOreDict("cellOxygen", 10),
						UtilsItems.getItemStackOfAmountFromOreDict("cellFluorine", 2),
						UtilsItems.getItemStackOfAmountFromOreDict("dustLithiumCarbonate", 3), //LithiumCarbonate
					}, //Output Array of Items - Upto 9,
					new int[]{0},
					75*20, //Time in ticks
					1000); //EU	

		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}
		try {

			CORE.RA.addDehydratorRecipe(
					new ItemStack[]{
							UtilsItems.getItemStackOfAmountFromOreDict("cellWater", 10)
					}, //Item input (Array, up to 2)
					FluidUtils.getFluidStack("uraniumtetrafluoride", 1440), //Fluid input (slot 1)
					null, //Fluid output (slot 2)
					new ItemStack[]{
						UtilsItems.getItemStackOfAmountFromOreDict("dustUraniumTetrafluoride", 10),
						UtilsItems.getItemStackOfAmountFromOreDictNoBroken("cellEmpty", 10)
					}, //Output Array of Items - Upto 9,
					new int[]{0},
					150*20, //Time in ticks
					2000); //EU	

		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}
		try {

			CORE.RA.addDehydratorRecipe(
					new ItemStack[]{
							UtilsItems.getItemStackOfAmountFromOreDict("cellWater", 10)
					}, //Item input (Array, up to 2)
					FluidUtils.getFluidStack("uraniumhexafluoride", 1440), //Fluid input (slot 1)
					null, //Fluid output (slot 2)
					new ItemStack[]{
						UtilsItems.getItemStackOfAmountFromOreDict("dustUraniumHexafluoride", 10),
						UtilsItems.getItemStackOfAmountFromOreDictNoBroken("cellEmpty", 10)
					}, //Output Array of Items - Upto 9,
					new int[]{0},
					300*20, //Time in ticks
					4000); //EU	

		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}
		
		//Raisins from Grapes
		try {
			
			CORE.RA.addDehydratorRecipe(
					new ItemStack[]{
							UtilsItems.getItemStackOfAmountFromOreDict("cropGrape", 1)
					}, //Item input (Array, up to 2)
					null, //Fluid input (slot 1)
					null, //Fluid output (slot 2)
					new ItemStack[]{
						UtilsItems.getItemStackOfAmountFromOreDict("foodRaisins", 1)
					}, //Output Array of Items - Upto 9,
					new int[]{0},
					10*20, //Time in ticks
					8); //EU	

		}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}

		//Calcium Hydroxide
		if (UtilsItems.getItemStackOfAmountFromOreDict("dustQuicklime", 1).getItem() != ModItems.AAA_Broken || LoadedMods.IHL){
			try {
				
				CORE.RA.addDehydratorRecipe(
						new ItemStack[]{
								UtilsItems.getItemStackOfAmountFromOreDict("dustQuicklime", 10)
						}, //Item input (Array, up to 2)
						FluidUtils.getFluidStack("water", 10000), //Fluid input (slot 1)
						null, //Fluid output (slot 2)
						new ItemStack[]{
							UtilsItems.getItemStackOfAmountFromOreDict("dustCalciumHydroxide", 20)
						}, //Output Array of Items - Upto 9,
						new int[]{0},
						120*20, //Time in ticks
						120); //EU	

			}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}
			
			//2 LiOH + CaCO3
			try {
				
				CORE.RA.addDehydratorRecipe(
						new ItemStack[]{
								UtilsItems.getItemStackOfAmountFromOreDict("dust2LiOHCaCO3", 5)
						}, //Item input (Array, up to 2)
						null, //Fluid input (slot 1)
						null, //Fluid output (slot 2)
						new ItemStack[]{
							UtilsItems.getItemStackOfAmountFromOreDict("dustLithiumHydroxide", 2),
							UtilsItems.getItemStackOfAmountFromOreDict("dustCalciumCarbonate", 3)
						}, //Output Array of Items - Upto 9,
						new int[]{0},
						120*20, //Time in ticks
						1000); //EU	

			}catch (NullPointerException e){Utils.LOG_INFO("FAILED TO LOAD RECIPES - NULL POINTER SOMEWHERE");}
		}
		
	}

	private static void assemblerRecipes(){
		//GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 6L), ItemList.Casing_Turbine.get(1L, new Object[0]), ItemList.Casing_Turbine2.get(1L, new Object[0]), 50, 16);
		//GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 6L), ItemList.Casing_Turbine.get(1L, new Object[0]), ItemList.Casing_Turbine3.get(1L, new Object[0]), 50, 16);

	}

	private static void distilleryRecipes(){
		Utils.LOG_INFO("Registering Distillery/Distillation Tower Recipes.");
		GT_Values.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), FluidUtils.getFluidStack("air", 1000), FluidUtils.getFluidStack("helium", 1), 400, 30, false);
		GT_Values.RA.addDistillationTowerRecipe(FluidUtils.getFluidStack("air", 20000), FluidUtils.getFluidStackArray("helium", 25), UtilsItems.getSimpleStack(ModItems.itemHeliumBlob, 1), 200, 60);
	}

	private static void addFuels(){
		Utils.LOG_INFO("Registering New Fuels.");
		GT_Values.RA.addFuel(UtilsItems.simpleMetaStack("EnderIO:bucketFire_water", 0, 1), null, 120, 0);
		GT_Values.RA.addFuel(UtilsItems.simpleMetaStack("EnderIO:bucketRocket_fuel", 0, 1), null, 112, 0);
		GT_Values.RA.addFuel(UtilsItems.simpleMetaStack("EnderIO:bucketHootch", 0, 1), null, 36, 0);



		//CORE.RA.addFuel(UtilsItems.simpleMetaStack("EnderIO:bucketRocket_fuel", 0, 1), null, 112, 0);
		GT_Values.RA.addFuel(UtilsItems.getSimpleStack(Items.lava_bucket), null, 32, 2);
		GT_Values.RA.addFuel(UtilsItems.getIC2Cell(2), null, 32, 2);
		GT_Values.RA.addFuel(UtilsItems.getIC2Cell(11), null, 24, 2);
		//System.exit(1);
	}

	private static void extractorRecipes(){
		Utils.LOG_INFO("Registering Extractor Recipes.");
		GT_ModHandler.addExtractionRecipe(GregtechItemList.Battery_RE_EV_Sodium.get(1L, new Object[0]), ItemList.Battery_Hull_HV.get(4L, new Object[0]));
		GT_ModHandler.addExtractionRecipe(GregtechItemList.Battery_RE_EV_Cadmium.get(1L, new Object[0]), ItemList.Battery_Hull_HV.get(4L, new Object[0]));
		GT_ModHandler.addExtractionRecipe(GregtechItemList.Battery_RE_EV_Lithium.get(1L, new Object[0]), ItemList.Battery_Hull_HV.get(4L, new Object[0]));
	}

	private static void chemicalBathRecipes(){
		int[] chances = {10000, 5000, 2500};
		GT_Values.RA.addChemicalBathRecipe(UtilsItems.getItemStackOfAmountFromOreDict("dustTin", 12), FluidUtils.getFluidStack("chlorine", 2400),
				UtilsItems.getItemStackOfAmountFromOreDict("dustZirconium", 1),
				UtilsItems.getItemStackOfAmountFromOreDict("dustZirconium", 1),
				UtilsItems.getItemStackOfAmountFromOreDict("dustZirconium", 1), 
				chances,
				30*20,
				240);
		
		GT_Values.RA.addChemicalBathRecipe(
				UtilsItems.getItemStackOfAmountFromOreDict("dustLithiumCarbonate", 10),
				FluidUtils.getFluidStack("hydrofluoricacid", 20000),
				UtilsItems.getItemStackOfAmountFromOreDict("dustLithiumFluoride", 5),
				null,
				null, 
				new int[]{},
				90*20,
				500);
	}
	
	private static void chemicalReactorRecipes(){
		GT_Values.RA.addChemicalRecipe(
				UtilsItems.getItemStackOfAmountFromOreDict("dustLithiumCarbonate", 5), //Input Stack 1
				UtilsItems.getItemStackOfAmountFromOreDict("dustCalciumHydroxide", 5), //Input Stack 2
				null, //Fluid Input
				null, //Fluid Output
				UtilsItems.getItemStackOfAmountFromOreDict("dust2LiOHCaCO3", 10), //Output Stack
				600*20
				);		
		
		GT_Values.RA.addChemicalRecipe(
				UtilsItems.getItemStackOfAmountFromOreDict("dustLithiumHydroxide", 5), //Input Stack 1
				null, //Input Stack 2
				FluidUtils.getFluidStack("hydrofluoricacid", 2500), //Fluid Input
				FluidUtils.getFluidStack("water", 2500), //Fluid Output
				UtilsItems.getItemStackOfAmountFromOreDict("dustLithiumFluoride", 5), //Output Stack
				600*20
				);	
	}
	
	private static void blastFurnaceRecipes(){
		GT_Values.RA.addBlastRecipe(
				UtilsItems.getItemStackOfAmountFromOreDict("dustLithiumFluoride", 2),
				UtilsItems.getItemStackOfAmountFromOreDict("dustBerylliumFluoride", 1),
				GT_Values.NF, GT_Values.NF,
				UtilsItems.getItemStackOfAmountFromOreDict("dustLi2BeF4", 3),
				null,
				60*20,
				2000, 
				3000);
	}


}