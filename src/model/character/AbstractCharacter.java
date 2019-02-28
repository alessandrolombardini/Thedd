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
public abstract class AbstractCharacter extends AbstractAutomaticActor implements BasicCharacter {

    private final EnumMap<Statistic, StatValues> stat;
    private final Inventory inventory;
    private final List<EquipableItem> equipment;

    /**
     * GenericCharacter's constructor.
     * 
     * @param name , the name of the character.
     */
    public AbstractCharacter(final String name) {
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
    public final void updateActualStat(final Statistic stat, final int value) {
        this.stat.get(stat).updateActual(value);
    }

    @Override
    public final void updateMaxStat(final Statistic stat, final int value) {
        this.stat.get(stat).updateMax(value);
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
        final Optional<EquipableItem> it = this.inventory.getEquipableItem(itemId);
        if (it.isPresent() && checkEquipment(it.get())) {
            this.equipment.add(it.get());
            this.inventory.removeItem(it.get());
            return true;
        }
        return false;
    }

    @Override
    public final void removeItem(final int itemId) {
        for (int i = 0; i < this.equipment.size(); i++) {
            if (this.equipment.get(i).getId() == itemId) {
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

    //Returns true if this Item is equipable, else false.
    private boolean checkEquipment(final EquipableItem item) {
        if (item.getType().isWeapon()) {
            if (this.equipment.stream()
                              .noneMatch(it -> it.getType() == EquipableItemType.TWO_HANDED)) {
                final int oneHandWeapons = (int) this.equipment.stream()
                                                               .filter(it -> it.getType() == EquipableItemType.ONE_HANDED)
                                                               .count();
                return (oneHandWeapons == 0 || (oneHandWeapons == 1 && item.getType() == EquipableItemType.ONE_HANDED));
            } 
            return false;
        } else if (item.getType() == EquipableItemType.RING) {
            return ((int) this.equipment.stream()
                                    .filter(it -> it.getType() == EquipableItemType.RING)
                                    .count() < EquipableItemType.getMaxNumOfRings());
        } else {
            return this.equipment.stream()
                                 .noneMatch(it -> it.getType() == item.getType());
        }
    }

}
