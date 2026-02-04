package fun.swip.vegans.mixin;

import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public class LeavingFurnaces {
    private static final java.util.Random RANDOM = new java.util.Random();
    private static final int MESSAGE_COUNT = 20;
    private static final String[] LEAVE_KEYS = {
            "msg.vegans.furnace_leave.1",
            "msg.vegans.furnace_leave.2",
            "msg.vegans.furnace_leave.3",
            "msg.vegans.furnace_leave.4",
            "msg.vegans.furnace_leave.5",
            "msg.vegans.furnace_leave.6",
            "msg.vegans.furnace_leave.7",
            "msg.vegans.furnace_leave.8",
            "msg.vegans.furnace_leave.9",
            "msg.vegans.furnace_leave.10",
            "msg.vegans.furnace_leave.11",
            "msg.vegans.furnace_leave.12",
            "msg.vegans.furnace_leave.13",
            "msg.vegans.furnace_leave.14",
            "msg.vegans.furnace_leave.15",
            "msg.vegans.furnace_leave.16",
            "msg.vegans.furnace_leave.17",
            "msg.vegans.furnace_leave.18",
            "msg.vegans.furnace_leave.19",
            "msg.vegans.furnace_leave.20"

    };

    @Inject(method = "setItem", at = @At("HEAD"))
    public void onSetStack(int slot, ItemStack stack, CallbackInfo ci) {
        if (slot == 1 && stack.is(Items.COAL)) {
            AbstractFurnaceBlockEntity furnace = (AbstractFurnaceBlockEntity)(Object)this;
            Level world = furnace.getLevel();
            if (stack.is(Items.COAL)) {
                if (world != null) {
                    world.removeBlock(furnace.getBlockPos(), false);
                    var server = world.getServer();

                    String randomKey = LEAVE_KEYS[RANDOM.nextInt(LEAVE_KEYS.length)];
                    String translatedString = Language.getInstance().getOrDefault(randomKey);
                    Component leaveMessage = Component.literal(translatedString).withStyle(net.minecraft.ChatFormatting.YELLOW);

                    assert server != null;
                    server.getPlayerList().broadcastSystemMessage(leaveMessage, false);
                }
            }
        }
    }
}
