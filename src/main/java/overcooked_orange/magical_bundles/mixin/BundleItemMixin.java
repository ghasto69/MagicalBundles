package overcooked_orange.magical_bundles.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import overcooked_orange.magical_bundles.bundle.MagicalBundleContents;

@Mixin(BundleItem.class)
public abstract class BundleItemMixin extends Item {
    public BundleItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyExpressionValue(
            method = {"onClicked"},
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/component/type/BundleContentsComponent;)Lnet/minecraft/component/type/BundleContentsComponent$Builder;"
            )
    )
    private BundleContentsComponent.Builder clicked(BundleContentsComponent.Builder original, ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        return ((MagicalBundleContents.Builder) original).magicalBundles$setCapacityMultiplier(MagicalBundleContents.calculateCapacityMultiplier(stack.getEnchantments(), player.getRandom()));
    }

    @ModifyExpressionValue(
            method = {"onStackClicked"},
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/component/type/BundleContentsComponent;)Lnet/minecraft/component/type/BundleContentsComponent$Builder;"
            )
    )
    private BundleContentsComponent.Builder clicked(BundleContentsComponent.Builder original, ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        return ((MagicalBundleContents.Builder) original).magicalBundles$setCapacityMultiplier(MagicalBundleContents.calculateCapacityMultiplier(stack.getEnchantments(), player.getRandom()));
    }

    @ModifyExpressionValue(
            method = "popFirstBundledStack",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/component/type/BundleContentsComponent;)Lnet/minecraft/component/type/BundleContentsComponent$Builder;"
            )
    )
    private static BundleContentsComponent.Builder popFirstBundledStack(BundleContentsComponent.Builder original, ItemStack stack, PlayerEntity player, BundleContentsComponent contents) {
        return ((MagicalBundleContents.Builder) original).magicalBundles$setCapacityMultiplier(MagicalBundleContents.calculateCapacityMultiplier(stack.getEnchantments(), player.getRandom()));
    }
}
