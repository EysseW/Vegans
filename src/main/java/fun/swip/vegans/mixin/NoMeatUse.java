package fun.swip.vegans.mixin;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(Consumable.class)
public class NoMeatUse {
    TagKey<Item> meatTag = TagKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath("minecraft", "meat")
    );
    TagKey<Item> fishTag = TagKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath("minecraft", "fishes")
    );



    static {
        System.out.println("NoMeatUse class loaded");
    }

    @Inject(
            method = "onConsume",
            at = @At("HEAD"),
            cancellable = true
    )

    public void finishConsumption(Level world, LivingEntity user, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        System.out.println("Item consumed!");
        //ConsumableComponent consumableComponent = (ConsumableComponent)stack.get(DataComponentTypes.CONSUMABLE);
        if (stack.is(meatTag) || stack.is(fishTag)) {
            if (user instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.disconnect(Component.literal("You were a vegan, remember?"));
            }
            System.out.println("meat!");
            cir.setReturnValue(stack);
        }
    }
}

