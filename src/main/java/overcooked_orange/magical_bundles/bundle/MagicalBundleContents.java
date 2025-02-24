package overcooked_orange.magical_bundles.bundle;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.math.Fraction;
import overcooked_orange.magical_bundles.MagicalBundles;
import overcooked_orange.magical_bundles.mixin.BundleContentsAccessor;

import java.util.List;

public interface MagicalBundleContents {
    float magicalBundles$getCapacityMultiplier();
    BundleContentsComponent magicalBundles$setCapacityMultiplier(float capacityMultiplier);

    static Fraction modifyOccupancy(Fraction original, float capacityMultiplier) {
        return original.divideBy(Fraction.getFraction(capacityMultiplier));
    }
    static float calculateCapacityMultiplier(ItemEnchantmentsComponent enchantments) {
        int level = enchantments.getEnchantmentEntries().stream().filter(entry -> entry.getKey().matchesKey(MagicalBundles.CAPACITY)).map(Object2IntMap.Entry::getIntValue).findFirst().orElse(0);
        return level == 0 ? 1 : 1 + level / 2f;
    }

    interface Builder {
        float magicalBundles$getCapacityMultiplier();
        BundleContentsComponent.Builder magicalBundles$setCapacityMultiplier(float capacityMultiplier);
    }
}
