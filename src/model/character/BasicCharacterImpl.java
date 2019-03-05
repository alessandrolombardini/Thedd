package model.character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import model.combat.implementations.AbstractAutomaticActor;
import model.item.EquipableItem;
import model.item.EquipableItemType;
import model.item.Item;

/**
 * Class that define a Generic Character.
 */
public class BasicCharacterImpl extends AbstractAutomaticActor implements BasicCharacter {

    private final EnumMap<Statistic, StatValues> stat;
    private final Inventory inventory;
    private final List<EquipableItem> equipment;
    private static final boolean LOG = true;

    /**
     * GenericCharacter's constructor.
     * 
     * @param name , the name of the character.
     */
    protected BasicCharacterImpl(final String name) {
        super(name);
        this.stat = new EnumMap<>(Statistic.class);
        this.inventory = new InventoryImpl();
        this.equipment = new ArrayList<>();
    }

    @Override
    public final void setBasicStat(final EnumMap<Statistic, StatValues> basicStat) {
        this.stat.putAll(basicStat);
    }

    @Override
    public final boolean isAlive() {
        return this.stat.get(Statistic.PV).getActual() > 0;
    }

    @Override
    public final StatValues getStat(final Statistic stat) {
        return this.stat.get(stat);
    }

    @Override
    public final boolean equipItem(final int itemId) {
        final Optional<Item> it = this.inventory.getItem(itemId);
        if (it.isPresent() && it.get().isEquipable()) {
            final EquipableItem equip = (EquipableItem) it.get();
            if (checkEquipment(equip)) {
                this.equipment.add((EquipableItem) it.get());
                equip.onEquip(this);
                this.inventory.removeItem(it.get());
            }
            return true;
        }
        return false;
    }

    @Override
    public final void unequipItem(final int itemId) {
        for (int i = 0; i < this.equipment.size(); i++) {
            if (this.equipment.get(i).getId() == itemId) {
                this.equipment.get(i).onUnequip(this);
                this.inventory.addItem(this.equipment.remove(i));
            }
        }
    }

    @Override
    public final List<? extends Item> getEquippedItems() {
        return Collections.unmodifiableList(this.equipment);
    }

    @Override
    public final Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public final int getPriority() {
        return this.stat.get(Statistic.RIFL).getActual();
    }

    // Returns true if this Item can be equipped, else false.
    private boolean checkEquipment(final EquipableItem item) {
        if (item.getType().isWeapon()) {
            if (this.equipment.stream().noneMatch(it -> it.getType() == EquipableItemType.TWO_HANDED)) {
                final int oneHandWeapons = (int) this.equipment.stream()
                        .filter(it -> it.getType() == EquipableItemType.ONE_HANDED).count();
                return (oneHandWeapons == 0 || (oneHandWeapons == 1 && item.getType() == EquipableItemType.ONE_HANDED));
            }
            return false;
        } else if (item.getType() == EquipableItemType.RING) {
            return ((int) this.equipment.stream().filter(it -> it.getType() == EquipableItemType.RING)
                    .count() < EquipableItemType.getMaxNumOfRings());
        } else {
            return this.equipment.stream().noneMatch(it -> it.getType() == item.getType());
        }
    }

    @Override
    public final void log() {
        if (LOG) {
            System.out.println("----------");
            System.out.println("Name: " + this.getName());
            System.out.println("Stat: " + this.stat);
            System.out.println("Equipment: " + this.equipment);
            System.out.println("Inventory: " + this.inventory.toString());
            System.out.println("----------");
        }
    }
}
