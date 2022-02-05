package me.jishuna.actionconfiglib.effects.entries;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "" })
@RegisterEffect(name = "SMELT")
public class SmeltEffect extends Effect {
	private static Map<Material, Material> smeltingMap;

	static {
		loadSmeltables();
	}

	public SmeltEffect(ConfigurationEntry entry) throws ParsingException {
	}

	@Override
	public void evaluate(ActionContext context) {
		if (context.getEvent() instanceof BlockDropItemEvent event) {
			for (Item item : event.getItems()) {
				handleItem(item);
			}
		}

		if (context.getEvent() instanceof PlayerFishEvent event && event.getCaught() instanceof Item item) {
			handleItem(item);
		}
	}

	private void handleItem(Item item) {
		ItemStack itemstack = item.getItemStack();
		Material material = itemstack.getType();
		Material smelted = smeltingMap.get(material);

		if (smelted != null) {
			itemstack.setType(smelted);
			item.setItemStack(itemstack);
		}
	}

	private static void loadSmeltables() {
		smeltingMap = new EnumMap<>(Material.class);
		Iterator<Recipe> iterator = Bukkit.recipeIterator();

		while (iterator.hasNext()) {
			Recipe recipe = iterator.next();

			if (recipe instanceof CookingRecipe) {
				CookingRecipe<?> furnaceRecipe = (CookingRecipe<?>) recipe;
				if (furnaceRecipe.getInputChoice() instanceof MaterialChoice) {
					for (Material input : ((MaterialChoice) furnaceRecipe.getInputChoice()).getChoices()) {
						smeltingMap.put(input, furnaceRecipe.getResult().getType());
					}
				} else {
					smeltingMap.put(furnaceRecipe.getInput().getType(), furnaceRecipe.getResult().getType());
				}
			}
		}
	}
}
