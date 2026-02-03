package fun.swip.vegans.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Language;
import net.minecraft.world.World;
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

    @Inject(method = "setStack", at = @At("HEAD"))
    public void onSetStack(int slot, ItemStack stack, CallbackInfo ci) {
        if (slot == 1 && stack.isOf(Items.COAL)) {
            AbstractFurnaceBlockEntity furnace = (AbstractFurnaceBlockEntity)(Object)this;
            World world = furnace.getWorld();
            if (stack.isOf(Items.COAL)) {
                if (world != null) {
                    world.removeBlock(furnace.getPos(), false);
                    var server = world.getServer();

                    String randomKey = LEAVE_KEYS[RANDOM.nextInt(LEAVE_KEYS.length)];
                    String translatedString = Language.getInstance().get(randomKey);
                    Text leaveMessage = Text.literal(translatedString).formatted(net.minecraft.util.Formatting.YELLOW);

                    assert server != null;
                    server.getPlayerManager().broadcast(leaveMessage, false);
                }
            }
        }
    }
}
