package model.character;

import java.util.HashMap;
import java.util.Map;
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
        if (findItem(id).isPresent()) {
            return Optional.of(findItem(id).get());
        } else {
            return Optional.empty();
        }
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
    public final void removeItem(final Item item) {
        this.items.put(item, this.items.get(item) - 1);
        if (this.items.get(item) <= 0) {
            this.items.remove(item);
        }
    }

    private Optional<Item> findItem(final int id) {
        return this.items.entrySet().stream().filter(en -> en.getKey().getId() == id).map(en -> en.getKey())
                .findFirst();
    }

    @Override
    public final String toString() {
        String ret = "";

        for (Map.Entry<Item, Integer> pair : this.items.entrySet()) {
            ret = ret + "[ Item: " + pair.getKey().toString() + "\nNumber: " + pair.getValue() + "]\n";
        }

        return ret;
//        return this.items.toString();
    }
}
