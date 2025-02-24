package overcooked_orange.magical_bundles;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicalBundles implements ModInitializer {
	public static final String MOD_ID = "magical_bundles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RegistryKey<Enchantment> CAPACITY = RegistryKey.of(RegistryKeys.ENCHANTMENT, toId("capacity"));

	@Override
	public void onInitialize() {
		EnchantmentEvents.ALLOW_ENCHANTING.register((registryEntry, itemStack, enchantingContext) -> {
			if(itemStack.hasChangedComponent(DataComponentTypes.BUNDLE_CONTENTS) && registryEntry.matchesKey(MagicalBundles.CAPACITY))
				return TriState.FALSE;

			return TriState.DEFAULT;
		});
	}

	public static Identifier toId(String path) {return Identifier.of(MOD_ID, path);}
}