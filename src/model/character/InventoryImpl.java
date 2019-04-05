package model.character;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import thedd.model.item.EquipableItem;
import thedd.model.item.Item;
import thedd.model.item.UsableItem;

import java.util.Optional;

/**
 * Implementation of Inventory interface.
 *
 */
public class InventoryImpl implements Inventory {

    private final Map<Item, Integer> items;

    /**
     * InventoryImpl constructor.
     */
    public InventoryImpl() {
        this.items = new HashMap<>();
    }

    @Override
    public final Optional<EquipableItem> getEquipableItem(final int id) {
        if (findItem(id).isPresent()) {
            final Entry<Item, Integer> entry = findItem(id).get();
            if (entry.getKey().isEquipable()) {
                // It's safe to do this cast because was checked that this item is Equipable
                return Optional.of((EquipableItem) entry.getKey());
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public final void addItem(final Item item) {
        if (this.items.containsKey(item)) {
            this.items.put(item, this.items.get(item) + 1);
        } else {
            this.items.put(item, 1);
        }
    }

    @Override
    public final Optional<UsableItem> getUsableItem(final int id) {
        if (findItem(id).isPresent()) {
            final Entry<Item, Integer> entry = findItem(id).get();
            if (!entry.getKey().isEquipable()) {
                // It's safe to do this cast because was checked that this item is Usable
                return Optional.of((UsableItem) entry.getKey());
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public final void removeItem(final int id) {
        // to-do
    }

    @Override
    public final void removeItem(final Item item) {
        this.items.put(item, this.items.get(item) - 1);
        if (this.items.get(item) <= 0) {
            this.items.remove(item);
        }
    }

    private Optional<Entry<Item, Integer>> findItem(final int id) {
        return this.items.entrySet().stream().filter(en -> en.getKey().getId() == id).findFirst();
    }
}
