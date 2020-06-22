package xyz.phanta.tconevo.client.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import slimeknights.tconstruct.library.tools.IToolPart;
import xyz.phanta.tconevo.client.gui.GuiPreviewMaterialRender;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandTconEvoClient extends CommandBase {

    private static final String LOC_BASE = "commands.tconevo.tconevoclient.";
    private static final String LOC_USAGE = LOC_BASE + "usage";

    private static final String LOC_MATRENDERINFO_BASE = LOC_BASE + "matrenderinfo.";
    private static final String LOC_MATRENDERINFO_FAILURE_NOPART = LOC_MATRENDERINFO_BASE + "failure_nopart";

    @Override
    public String getName() {
        return "tconevoclient";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return LOC_USAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Minecraft mc = Minecraft.getMinecraft();
        if (args.length < 1) {
            throw new WrongUsageException(LOC_USAGE);
        }
        if (args[0].equals("matrenderinfo")) {
            ItemStack stack = mc.player.getHeldItemMainhand();
            if (stack.isEmpty() || !(stack.getItem() instanceof IToolPart)) {
                throw new CommandException(LOC_MATRENDERINFO_FAILURE_NOPART);
            }
            IToolPart part = (IToolPart)stack.getItem();
            // we can't open a gui from the command directly, so we need to schedule a task to run later
            // however, tasks scheduled with the client scheduler on the client thread will run immediately, so that's no good
            // thus, we schedule a task with the world scheduler, which then schedules a task with the client scheduler
            // absolutely gross
            server.addScheduledTask(() -> mc.addScheduledTask(() -> mc.displayGuiScreen(
                    new GuiPreviewMaterialRender(part.getMaterial(stack), part))));
        } else {
            throw new WrongUsageException(LOC_USAGE);
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "matrenderinfo");
        }
        return Collections.emptyList();
    }

}
