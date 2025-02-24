package overcooked_orange.magical_bundles.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import overcooked_orange.magical_bundles.bundle.MagicalBundleContents;

import java.util.List;
import java.util.function.Function;

@Mixin(BundleContentsComponent.class)
public abstract class BundleContentsMixin implements MagicalBundleContents {
    @Shadow @Final private Fraction occupancy;
    @Unique
    private float magicalBundles$capacityMultiplier = 1;

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/serialization/Codec;flatXmap(Ljava/util/function/Function;Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;"
            )
    )
    private static Codec<BundleContentsComponent> modifyCodec(Codec<BundleContentsComponent> original) {
        return RecordCodecBuilder.create(instance -> instance.group(
                original.fieldOf("value").forGetter(Function.identity()),
                Codec.FLOAT.fieldOf("capacity_multiplier").forGetter(bundleContents -> ((MagicalBundleContents) (Object) bundleContents).magicalBundles$getCapacityMultiplier())
        ).apply(instance, (bundleContents, capacityMultiplier) -> {
            List<ItemStack> stacks = ((BundleContentsAccessor) (Object) bundleContents).getStacks();
            return ((MagicalBundleContents) (Object) BundleContentsAccessor.init(stacks, MagicalBundleContents.modifyOccupancy(BundleContentsAccessor.calculateOccupancy(stacks), capacityMultiplier), bundleContents.getSelectedStackIndex())).magicalBundles$setCapacityMultiplier(capacityMultiplier);
        }));
    }

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/codec/PacketCodec;xmap(Ljava/util/function/Function;Ljava/util/function/Function;)Lnet/minecraft/network/codec/PacketCodec;"
            )
    )
    private static PacketCodec<RegistryByteBuf, BundleContentsComponent> modifyPacketCodec(PacketCodec<RegistryByteBuf, BundleContentsComponent> original) {
        return PacketCodec.tuple(
                original,
                Function.identity(),
                PacketCodecs.FLOAT,
                bundleContents -> ((MagicalBundleContents) (Object) bundleContents).magicalBundles$getCapacityMultiplier(),
                (bundleContents, capacityMultiplier) -> {
                    List<ItemStack> stacks = ((BundleContentsAccessor) (Object) bundleContents).getStacks();
                    return ((MagicalBundleContents) (Object) BundleContentsAccessor.init(stacks, MagicalBundleContents.modifyOccupancy(BundleContentsAccessor.calculateOccupancy(stacks), capacityMultiplier), bundleContents.getSelectedStackIndex())).magicalBundles$setCapacityMultiplier(capacityMultiplier);
                }
        );
    }

    @Override
    public float magicalBundles$getCapacityMultiplier() {
        return this.magicalBundles$capacityMultiplier;
    }

    @Override
    public BundleContentsComponent magicalBundles$setCapacityMultiplier(float capacityMultiplier) {
        this.magicalBundles$capacityMultiplier = capacityMultiplier;
        return (BundleContentsComponent) (Object) this;
    }

    @ModifyExpressionValue(
            method = "equals",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/commons/lang3/math/Fraction;equals(Ljava/lang/Object;)Z"
            )
    )
    private boolean equals(boolean original, Object object) {
        return original && ((MagicalBundleContents) object).magicalBundles$getCapacityMultiplier() == magicalBundles$getCapacityMultiplier();
    }

    @Mixin(BundleContentsComponent.Builder.class)
    public static abstract class Builder implements MagicalBundleContents.Builder {
        @Shadow
        @Final
        private List<ItemStack> stacks;

        @Shadow
        protected abstract int getMaxAllowed(ItemStack stack);

        @Unique
        private float magicalBundles$capacityMultiplier;

        @Inject(
                method = "<init>",
                at = @At("TAIL")
        )
        private void init(BundleContentsComponent base, CallbackInfo ci) {
            this.magicalBundles$setCapacityMultiplier(((MagicalBundleContents) (Object) base).magicalBundles$getCapacityMultiplier());
        }

        @ModifyReturnValue(
                method = "build",
                at = @At("TAIL")
        )
        private BundleContentsComponent build(BundleContentsComponent original) {
            return ((MagicalBundleContents) (Object) original).magicalBundles$setCapacityMultiplier(magicalBundles$getCapacityMultiplier());
        }

        @Override
        public BundleContentsComponent.Builder magicalBundles$setCapacityMultiplier(float capacityMultiplier) {
            this.magicalBundles$capacityMultiplier = capacityMultiplier;
            return (BundleContentsComponent.Builder) (Object) this;
        }

        @Override
        public float magicalBundles$getCapacityMultiplier() {
            return this.magicalBundles$capacityMultiplier;
        }

        @ModifyExpressionValue(
                method = {"getMaxAllowed", "add(Lnet/minecraft/item/ItemStack;)I", "removeSelected"},
                at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/BundleContentsComponent;getOccupancy(Lnet/minecraft/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;")
        )
        private Fraction modifyOccupancy(Fraction original) {
            return MagicalBundleContents.modifyOccupancy(original, magicalBundles$capacityMultiplier);
        }

        @Definition(id = "j", local = @Local(type = int.class, ordinal = 1))
        @Expression("j != -1")
        @ModifyExpressionValue(method = "add(Lnet/minecraft/item/ItemStack;)I", at = @At("MIXINEXTRAS:EXPRESSION"))
        private boolean fixPacketProblem(boolean original, @Local(ordinal = 0) int amount, @Local(ordinal = 1) int index) {
            return original && this.stacks.get(index).getCount() + amount <= 64;
        }

        @ModifyReturnValue(
                method = "getInsertionIndex",
                at = @At(value = "RETURN", ordinal = 1)
        )
        private int chooseBetter(int original, ItemStack stack) {
            for (int i = 0; i < this.stacks.size(); i++) {
                if (ItemStack.areItemsAndComponentsEqual(this.stacks.get(i), stack) && this.stacks.get(i).getCount() + this.getMaxAllowed(stack) <= 64) {
                    return i;
                }
            }

            return original;
        }
    }
}