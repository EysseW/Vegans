package fun.swip.vegans.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(ConsumableComponent.class)
public class NoMeatUse {
    TagKey<Item> meatTag = TagKey.of(
            RegistryKeys.ITEM,
            Identifier.of("minecraft", "meat")
    );
    TagKey<Item> fishTag = TagKey.of(
            RegistryKeys.ITEM,
            Identifier.of("minecraft", "fishes")
    );



    static {
        System.out.println("NoMeatUse class loaded");
    }

    @Inject(
            method = "finishConsumption",
            at = @At("HEAD"),
            cancellable = true
    )

    public void finishConsumption(World world, LivingEntity user, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        System.out.println("Item consumed!");
        //ConsumableComponent consumableComponent = (ConsumableComponent)stack.get(DataComponentTypes.CONSUMABLE);
        if (stack.isIn(meatTag) || stack.isIn(fishTag)) {
            if (user instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.networkHandler.disconnect(Text.literal("You were a vegan, remember?"));
            }
            System.out.println("meat!");
            cir.setReturnValue(stack);
        }
    }
}

