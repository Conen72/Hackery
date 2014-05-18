package guru.haun.hackery.items;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import guru.haun.hackery.HackeryMod;
import guru.haun.hackery.potion.HackPotions;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;

public class GlitchPowder extends Item{
	
	public GlitchPowder(boolean register) {
		setUnlocalizedName("powderGlitch");
		setTextureName("hackery:GlitchPowder");
		if(register){
			GameRegistry.registerItem(this, "glitchPowder");
			GameRegistry.addShapelessRecipe(new ItemStack(this, 1,0), new ItemStack(HackeryMod.shardGlitch,1,0), new ItemStack(HackeryMod.shardGlitch,1,0), new ItemStack(HackeryMod.shardGlitch,1,0), new ItemStack(HackeryMod.shardGlitch,1,0));
			registerPotion();
		}
	}
	
	@Override
	public boolean isPotionIngredient(ItemStack is){
		return true;
	}
	
	public String getPotionEffect(ItemStack is){
		return "+0+1-2+3-13";
	}
	
	public void registerPotion(){
		try{
			Class potHepler = PotionHelper.class;
			Field potFields[] = potHepler.getDeclaredFields();
			for(Field f : potFields){
				if(f.getName() == "potionRequirements" || f.getName() == "field_77927_l"){
					Field mods = Field.class.getDeclaredField("modifiers");
					mods.setAccessible(true);
					mods.setInt(f, f.getModifiers() & ~Modifier.FINAL);
					f.setAccessible(true);
					final HashMap<Integer,String> newReq = (HashMap<Integer,String>)((HashMap<Integer,String>)f.get(null)).clone();
					newReq.put(Integer.valueOf(HackPotions.potionGFId), "0 1 !2 3 !13");
					f.set(null, newReq);
				}else if(f.getName() == "potionAmplifiers" || f.getName() == "field_77928_m"){
					;
				}
			}
		}catch (Exception e){
			HackeryMod.logger.error("Something went wrong while registering potions!");
			e.printStackTrace();
		}
		
	}

}
