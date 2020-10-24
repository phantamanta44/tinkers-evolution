package xyz.phanta.tconevo.command;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.artifact.Artifact;
import xyz.phanta.tconevo.integration.conarm.ConArmHooks;
import xyz.phanta.tconevo.integration.gamestages.GameStagesHooks;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandTconEvo extends CommandBase {

    private static final String LOC_BASE = "commands.tconevo.tconevo.";
    private static final String LOC_USAGE = LOC_BASE + "usage";

    private static final String LOC_MODADD_BASE = LOC_BASE + "modadd.";
    private static final String LOC_MODADD_USAGE = LOC_MODADD_BASE + "usage";
    private static final String LOC_MODADD_SUCCESS_PARTIAL = LOC_MODADD_BASE + "success_partial";
    private static final String LOC_MODADD_SUCCESS_TOTAL = LOC_MODADD_BASE + "success_total";
    private static final String LOC_MODADD_FAILURE = LOC_MODADD_BASE + "failure";

    private static final String LOC_MODMAX_BASE = LOC_BASE + "modmax.";
    private static final String LOC_MODMAX_USAGE = LOC_MODMAX_BASE + "usage";
    private static final String LOC_MODMAX_SUCCESS = LOC_MODMAX_BASE + "success";
    private static final String LOC_MODMAX_FAILURE = LOC_MODMAX_BASE + "failure";

    private static final String LOC_ARTIFACTGET_BASE = LOC_BASE + "artifactget.";
    private static final String LOC_ARTIFACTGET_USAGE = LOC_ARTIFACTGET_BASE + "usage";
    private static final String LOC_ARTIFACTGET_SUCCESS = LOC_ARTIFACTGET_BASE + "success";
    private static final String LOC_ARTIFACTGET_FAILURE = LOC_ARTIFACTGET_BASE + "failure";

    private static final String LOC_ARTIFACTRELOAD_BASE = LOC_BASE + "artifactreload.";
    private static final String LOC_ARTIFACTRELOAD_SUCCESS = LOC_ARTIFACTRELOAD_BASE + "success";

    @Override
    public String getName() {
        return "tconevo";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return LOC_USAGE;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = getSenderPlayer(sender);
        if (args.length < 1) {
            throw new WrongUsageException(LOC_USAGE);
        }
        switch (args[0]) {
            case "modadd": {
                if (args.length < 2 || args.length > 3) {
                    throw new WrongUsageException(LOC_MODADD_USAGE);
                }
                IModifier mod = getModifier(args[1]);
                int level = 1;
                if (args.length >= 3) {
                    level = parseInt(args[2], 1);
                }
                ItemStack orig = getToolStack(player);
                ItemStack stack = orig.copy();
                int freeModCount = ToolUtils.getAndSetModifierCount(stack, Short.MAX_VALUE);
                int remaining = level;
                String error = null;
                while (remaining > 0) {
                    try {
                        if (!mod.canApply(stack, orig)) {
                            break;
                        }
                    } catch (TinkerGuiException e) {
                        error = e.getMessage();
                        break;
                    }
                    mod.apply(stack);
                    try {
                        GameStagesHooks.INSTANCE.startBypass();
                        TinkerCraftingEvent.ToolModifyEvent.fireEvent(stack, player, orig.copy());
                    } catch (TinkerGuiException e) {
                        throw new CommandException(LOC_MODADD_FAILURE, e.getMessage());
                    } finally {
                        GameStagesHooks.INSTANCE.endBypass();
                    }
                    orig = stack.copy();
                    --remaining;
                }
                if (remaining == level) {
                    throw new CommandException(LOC_MODADD_FAILURE, error != null ? error : "...?");
                }
                try {
                    ToolUtils.rebuildToolStack(stack);
                } catch (TinkerGuiException e) {
                    // this almost certainly only occurs when there aren't enough modifiers, which we don't care about
                }
                ToolUtils.getAndSetModifierCount(stack, freeModCount);
                player.setHeldItem(EnumHand.MAIN_HAND, stack);
                if (remaining == 0) {
                    player.sendMessage(new TextComponentTranslation(LOC_MODADD_SUCCESS_TOTAL,
                            mod.getLocalizedName(), level));
                } else {
                    player.sendMessage(new TextComponentTranslation(LOC_MODADD_SUCCESS_PARTIAL,
                            mod.getLocalizedName(), level - remaining));
                }
                break;
            }
            case "modmax": {
                if (args.length != 1) {
                    throw new WrongUsageException(LOC_MODMAX_USAGE);
                }
                ItemStack orig = getToolStack(player);
                ItemStack stack = orig.copy();
                int freeModCount = ToolUtils.getAndSetModifierCount(stack, Short.MAX_VALUE);
                // getModifiers copies modifiers into a new list, so this shouldn't cause CMEs
                for (IModifier mod : TinkerUtil.getModifiers(stack)) {
                    // only maximize modifiers and ignore the extra-modifier modifier
                    if (mod == TinkerModifiers.modCreative
                            || !(mod instanceof ModifierTrait || mod instanceof ToolModifier
                            || ConArmHooks.INSTANCE.isArmourModifierTrait(mod))) {
                        continue;
                    }
                    for (int i = 0; i < 32767; i++) { // just for safety
                        try {
                            if (!mod.canApply(stack, orig)) {
                                break;
                            }
                        } catch (TinkerGuiException e) {
                            break;
                        }
                        mod.apply(stack);
                        try {
                            GameStagesHooks.INSTANCE.startBypass();
                            TinkerCraftingEvent.ToolModifyEvent.fireEvent(stack, player, orig.copy());
                        } catch (TinkerGuiException e) {
                            throw new CommandException(LOC_MODMAX_FAILURE, e.getMessage());
                        } finally {
                            GameStagesHooks.INSTANCE.endBypass();
                        }
                        orig = stack.copy();
                    }
                }
                try {
                    ToolUtils.rebuildToolStack(stack);
                } catch (TinkerGuiException e) {
                    // this almost certainly only occurs when there aren't enough modifiers, which we don't care about
                }
                ToolUtils.getAndSetModifierCount(stack, freeModCount);
                player.setHeldItem(EnumHand.MAIN_HAND, stack);
                player.sendMessage(new TextComponentTranslation(LOC_MODMAX_SUCCESS));
                break;
            }
            case "artifactget": {
                if (args.length != 2) {
                    throw new WrongUsageException(LOC_ARTIFACTGET_USAGE);
                }
                Artifact<?> artifact = TconEvoMod.PROXY.getArtifactRegistry().getArtifact(args[1]);
                if (artifact == null) {
                    throw new CommandException(LOC_ARTIFACTGET_FAILURE, args[1]);
                }
                player.inventory.addItemStackToInventory(artifact.newStack());
                player.sendMessage(new TextComponentTranslation(LOC_ARTIFACTGET_SUCCESS, artifact.getEntryName()));
                break;
            }
            case "artifactreload":
                TconEvoMod.PROXY.getArtifactRegistry().getLoader().loadArtifacts();
                TconEvoMod.PROXY.getArtifactRegistry().initArtifacts();
                player.sendMessage(new TextComponentTranslation(LOC_ARTIFACTRELOAD_SUCCESS));
                break;
            default:
                throw new WrongUsageException(LOC_USAGE);
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "modadd", "modmax", "artifactget", "artifactreload");
        } else if (args[0].equals("modadd") && args.length == 2) {
            List<String> modifierIds = new ArrayList<>();
            for (IModifier mod : TinkerRegistry.getAllModifiers()) {
                modifierIds.add(mod.getIdentifier());
            }
            return getListOfStringsMatchingLastWord(args, modifierIds);
        } else if (args[0].equals("artifactget") && args.length == 2) {
            return getListOfStringsMatchingLastWord(args, TconEvoMod.PROXY.getArtifactRegistry().getAllArtifactIds());
        }
        return Collections.emptyList();
    }

    public static EntityPlayer getSenderPlayer(ICommandSender sender) throws CommandException {
        if (!(sender instanceof EntityPlayer)) {
            throw new PlayerNotFoundException("commands.tconevo.generic.player_only");
        }
        return (EntityPlayer)sender;
    }

    public static ItemStack getToolStack(EntityPlayer sender) throws CommandException {
        ItemStack stack = sender.getHeldItemMainhand();
        if (stack.isEmpty() || !(stack.getItem() instanceof ITinkerable)) {
            throw new CommandException("commands.tconevo.generic.req_tool");
        }
        return stack;
    }

    public static IModifier getModifier(String id) throws CommandException {
        IModifier mod = TinkerRegistry.getModifier(id);
        if (mod == null) {
            throw new CommandException("commands.tconevo.generic.unknown_mod", id);
        }
        return mod;
    }

}
