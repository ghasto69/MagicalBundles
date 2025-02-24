package overcooked_orange.magical_bundles.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = {"net/minecraft/screen/GrindstoneScreenHandler$2", "net/minecraft/screen/GrindstoneScreenHandler$3"})
public abstract class GrindstoneScreenHandlerMixin {
    @ModifyReturnValue(
            method = "canInsert",
            at = @At("RETURN")
    )
    private boolean canInsert(boolean original, ItemStack stack) {
        return original && !stack.hasChangedComponent(DataComponentTypes.BUNDLE_CONTENTS);
    }
}
