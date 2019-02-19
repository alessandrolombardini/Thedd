package model.character;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import model.item.Item;

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
    public final Optional<Item> getItem(final int id) {
        final Optional<Entry<Item, Integer>> selected = this.items.entrySet().stream().
                filter(en -> en.getKey().getId() == id).findFirst();
        if (selected.isPresent()) {
            selected.get().setValue(selected.get().getValue() - 1);
                if (selected.get().getValue() <= 0) {
                    this.items.remove(selected.get().getKey());
                }
            return Optional.of(selected.get().getKey());
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
}
