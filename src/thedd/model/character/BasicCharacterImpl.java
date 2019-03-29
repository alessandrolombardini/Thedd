package thedd.model.character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import model.combat.implementations.AbstractAutomaticActor;
import model.item.EquipableItem;
import model.item.EquipableItemType;
import model.item.Item;
import thedd.model.character.inventory.Inventory;
import thedd.model.character.inventory.InventoryImpl;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.Statistic;

/**
 * Class that define a Generic Character.
 */
public class BasicCharacterImpl extends AbstractAutomaticActor implements BasicCharacter {

    private final EnumMap<Statistic, StatValues> stat;
    private final Inventory inventory;
    private final List<EquipableItem> equipment;

    private int hash;

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
        if (stat == null) {
            throw new IllegalArgumentException();
        }
        return this.stat.get(stat);
    }

    @Override
    public final EnumMap<Statistic, StatValues> getAllStat() {
        final EnumMap<Statistic, StatValues> ret = new EnumMap<>(Statistic.class);
        this.stat.entrySet().forEach(el -> {
            ret.put(el.getKey(), el.getValue());
        }); // done a defence copy
        return ret;
    }

    @Override
    public final boolean equipItem(final Item item) {
        if (item == null) {
            return false;
        }
        if (item.isEquipable()) {
            final EquipableItem equipItem = (EquipableItem) item;
            if (checkEquipment(equipItem)) {
                this.equipment.add((EquipableItem) equipItem);
                equipItem.onEquip(this);
                this.inventory.removeItem(item);
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean unequipItem(final Item item) {
        if (item == null) {
            return false;
        }
        final int index = equipment.indexOf(item);
        if (index == -1) {
            return false;
        }
        this.equipment.get(index).onUnequip(this);
        this.inventory.addItem(equipment.remove(index));
        return true;
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
    public final String toString() {
        return "Name: " + this.getName() + " - Stat: " + this.stat + "\nEquipment: " + this.equipment + " - Inventory: "
                + this.inventory.toString();
    }

    @Override
    public final int hashCode() {
        if (hash == 0) {
            hash = equipment.hashCode() ^ inventory.hashCode() ^ stat.hashCode();
        }
        return hash;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof BasicCharacterImpl) {
            final BasicCharacterImpl other = (BasicCharacterImpl) obj;
            return inventory.equals(other.getInventory()) && stat.equals(other.getAllStat())
                    && equipment.equals(other.getEquippedItems());
        }
        return false;
    }
}
