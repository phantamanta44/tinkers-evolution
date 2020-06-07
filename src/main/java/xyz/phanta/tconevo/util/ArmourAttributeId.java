package xyz.phanta.tconevo.util;

import net.minecraft.inventory.EntityEquipmentSlot;
import xyz.phanta.tconevo.TconEvoConfig;

import java.util.UUID;
import java.util.stream.Stream;

public class ArmourAttributeId {

    private static final UUID UNKNOWN = UUID.fromString("76dd2b8a-6029-431a-863d-30bb2adcd778");

    private final UUID helmet;
    private final UUID chestplate;
    private final UUID leggings;
    private final UUID boots;

    public ArmourAttributeId(String helmet, String chestplate, String leggings, String boots) {
        this.helmet = UUID.fromString(helmet);
        this.chestplate = UUID.fromString(chestplate);
        this.leggings = UUID.fromString(leggings);
        this.boots = UUID.fromString(boots);
    }

    public UUID getId(EntityEquipmentSlot slot) {
        return TconEvoConfig.armourSwitch(slot, helmet, chestplate, leggings, boots, UNKNOWN);
    }

    public Stream<UUID> streamIds() {
        return Stream.of(helmet, chestplate, leggings, boots);
    }

}
