package overcooked_orange.magical_bundles;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;

public class MagicalBundlesDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider((dataOutput, completableFuture) -> new FabricDynamicRegistryProvider(dataOutput, completableFuture) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
                entries.addAll(wrapperLookup.getOrThrow(RegistryKeys.ENCHANTMENT));
            }

            @Override
            public String getName() {
                return "Dynamic Registries";
            }
        });

        pack.addProvider((dataOutput, completableFuture) -> new FabricLanguageProvider(dataOutput, completableFuture) {
            @Override
            public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translations) {
                translations.addEnchantment(MagicalBundles.CAPACITY, "Capacity");
            }
        });

        pack.addProvider((dataOutput, completableFuture) -> new FabricTagProvider<>(dataOutput, RegistryKeys.ENCHANTMENT, completableFuture) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
                getOrCreateTagBuilder(EnchantmentTags.TREASURE)
                        .add(MagicalBundles.CAPACITY);
            }
        });
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, context -> context.register(MagicalBundles.CAPACITY,
                Enchantment.builder(Enchantment.definition(
                                context.getRegistryLookup(RegistryKeys.ITEM).getOrThrow(ItemTags.BUNDLES),
                                12,
                                2,
                                Enchantment.leveledCost(1, 10),
                                Enchantment.leveledCost(1, 15),
                                4,
                                AttributeModifierSlot.ANY
                        ))
                        .addNonListEffect(MagicalBundles.CAPACITY_EFFECT, new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(0.5f)))
                        .build(MagicalBundles.CAPACITY.getValue())
        ));
    }
}
