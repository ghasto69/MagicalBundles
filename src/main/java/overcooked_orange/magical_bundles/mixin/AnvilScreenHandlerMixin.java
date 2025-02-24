package overcooked_orange.magical_bundles.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    @ModifyExpressionValue(
            method = "updateResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;canHaveEnchantments(Lnet/minecraft/item/ItemStack;)Z"
            )
    )
    private boolean capacity(boolean original, @Local ItemStack stack) {
        return original && !stack.hasChangedComponent(DataComponentTypes.BUNDLE_CONTENTS);
    }
}
