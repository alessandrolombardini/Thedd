package model.character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import model.item.Item;

/**
 * Class that define a Generic Character.
 */
public abstract class AbstractCharacter implements Character {

    private final EnumMap<Statistic, StatValues> stat;
    private final Inventory inventory;
    private final List<Item> equipment;

    /**
     * GenericCharacter's constructor.
     * 
     * @param basicStat , a map with the basic statistic values of the character.
     */
    public AbstractCharacter(final EnumMap<Statistic, StatValues> basicStat) {
        this.stat = new EnumMap<>(basicStat);
        this.inventory = new InventoryImpl();
        this.equipment = new ArrayList<>();
    }

    @Override
    public final void updateStat(final Statistic stat, final int value) {
        this.stat.get(stat).updateActual(value);
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
    public final void equipItem(final int itemId) {
        final Optional<Item> it = this.inventory.getItem(itemId);
        if (it.isPresent()) { // Next to-do: add controls for type equipment.
            this.equipment.add(it.get());
        }
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

}
