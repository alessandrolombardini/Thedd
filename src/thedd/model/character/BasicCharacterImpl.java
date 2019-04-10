package thedd.model.character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import model.combat.actor.AbstractAutomaticActor;
import model.item.Item;
import model.item.equipableitem.EquipableItem;
import model.item.equipableitem.EquipableItemType;
import thedd.model.character.inventory.Inventory;
import thedd.model.character.inventory.InventoryImpl;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.StatValuesImpl;
import thedd.model.character.statistics.Statistic;

/**
 * Implementation of {@link thedd.model.character.BasicCharacter}.
 */
public class BasicCharacterImpl extends AbstractAutomaticActor implements BasicCharacter {

    private final EnumMap<Statistic, StatValues> stat;
    private final Inventory inventory;
    private final List<EquipableItem> equipment;
    private int hash;

    /**
     * GenericCharacter's constructor.
     * 
     * @param name       the name of the character.
     * @param multiplier rate multiplied at the basic statistics.
     */
    protected BasicCharacterImpl(final String name, final double multiplier) {
        super(name);
        this.stat = new EnumMap<>(Statistic.class);
        setBasicStat(multiplier);
        this.inventory = new InventoryImpl();
        this.equipment = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return this.stat.get(Statistic.HEALTH_POINT).getActual() > 0;
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
            throw new IllegalArgumentException();
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
            throw new IllegalArgumentException();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        return this.stat.get(Statistic.AGILITY).getActual();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Name: " + this.getName() + " - Stat: " + this.stat + "\nEquipment: " + this.equipment + " - Inventory: "
                + this.inventory.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = equipment.hashCode() ^ inventory.hashCode() ^ stat.hashCode() ^ super.hash();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof BasicCharacterImpl) {
            final BasicCharacterImpl other = (BasicCharacterImpl) obj;
            return inventory.equals(other.getInventory()) && stat.equals(other.getAllStat())
                    && equipment.equals(other.getEquippedItems());
        }
        return false;
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

    // This method sets basic statistics of the character.
    private void setBasicStat(final double multiplier) {
        this.stat.put(Statistic.HEALTH_POINT,
                new StatValuesImpl((int) (Statistic.HEALTH_POINT.getBasicValue() * multiplier)));
        this.stat.put(Statistic.AGILITY, new StatValuesImpl((int) (Statistic.AGILITY.getBasicValue() * multiplier)));
        this.stat.put(Statistic.CONSTITUTION,
                new StatValuesImpl((int) (Statistic.CONSTITUTION.getBasicValue() * multiplier)));
        this.stat.put(Statistic.STRENGTH, new StatValuesImpl((int) (Statistic.STRENGTH.getBasicValue() * multiplier)));
    }
}
