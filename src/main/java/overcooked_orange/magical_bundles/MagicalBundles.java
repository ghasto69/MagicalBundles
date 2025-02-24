package overcooked_orange.magical_bundles;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicalBundles implements ModInitializer {
    public static final String MOD_ID = "magical_bundles";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final RegistryKey<Enchantment> CAPACITY = RegistryKey.of(RegistryKeys.ENCHANTMENT, toId("capacity"));
    public static final ComponentType<EnchantmentValueEffect> CAPACITY_EFFECT = Registry.register(
            Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE,
            toId("capacity_effect"),
            ComponentType.<EnchantmentValueEffect>builder().codec(EnchantmentValueEffect.CODEC).build()
    );

    @Override
    public void onInitialize() {
        EnchantmentEvents.ALLOW_ENCHANTING.register((registryEntry, itemStack, enchantingContext) -> {
            if (itemStack.hasChangedComponent(DataComponentTypes.BUNDLE_CONTENTS) && registryEntry.value().effects().contains(MagicalBundles.CAPACITY_EFFECT))
                return TriState.FALSE;

            return TriState.DEFAULT;
        });
    }

    public static Identifier toId(String path) {
        return Identifier.of(MOD_ID, path);
    }
}