package overcooked_orange.magical_bundles.bundle;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.util.math.random.Random;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.mutable.MutableFloat;
import overcooked_orange.magical_bundles.MagicalBundles;

public interface MagicalBundleContents {
    float magicalBundles$getCapacityMultiplier();
    BundleContentsComponent magicalBundles$setCapacityMultiplier(float capacityMultiplier);

    static Fraction modifyOccupancy(Fraction original, float capacityMultiplier) {
        return original.divideBy(Fraction.getFraction(capacityMultiplier));
    }

    static float calculateCapacityMultiplier(ItemEnchantmentsComponent enchantments, Random random) {
        MutableFloat capacityMultiplier = new MutableFloat(1.0f);
        enchantments.getEnchantmentEntries().forEach(entry -> {
            Enchantment enchantment = entry.getKey().value();
            EnchantmentValueEffect effect = enchantment.effects().get(MagicalBundles.CAPACITY_EFFECT);
            if(effect != null)
                enchantment.modifyValue(MagicalBundles.CAPACITY_EFFECT, random, entry.getIntValue(), capacityMultiplier);
        });
        return capacityMultiplier.floatValue();
    }

    interface Builder {
        float magicalBundles$getCapacityMultiplier();
        BundleContentsComponent.Builder magicalBundles$setCapacityMultiplier(float capacityMultiplier);
    }
}
