package overcooked_orange.magical_bundles.mixin;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(BundleContentsComponent.class)
public interface BundleContentsAccessor {
    @Invoker(value = "<init>")
    static BundleContentsComponent init(List<ItemStack> stacks, Fraction occupancy, int selectedStackIndex) {throw new AssertionError();}

    @Invoker(value = "calculateOccupancy")
    static Fraction calculateOccupancy(List<ItemStack> stacks) {throw new AssertionError();}

    @Accessor
    List<ItemStack> getStacks();
}
