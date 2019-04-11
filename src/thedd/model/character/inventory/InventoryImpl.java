package thedd.model.character.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import thedd.model.item.Item;

/**
 * Implementation of {@link thedd.model.character.inventory.Inventory}.
 *
 */
public final class InventoryImpl implements Inventory {

    private final Map<Item, Integer> items;
    private int hash;

    /**
     * InventoryImpl constructor.
     */
    public InventoryImpl() {
        this.items = new HashMap<>();
    }

    @Override
    public void addItem(final Item item) {
        Objects.requireNonNull(item);
        if (this.items.containsKey(item)) {
            this.items.put(item, this.items.get(item) + 1);
        } else {
            this.items.put(item, 1);
        }
    }

    @Override
    public void removeItem(final Item item) {
        Objects.requireNonNull(item);
        if (!this.items.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        this.items.put(item, this.items.get(item) - 1);
        if (this.items.get(item) <= 0) {
            this.items.remove(item);
        }
    }

    @Override
    public String toString() {
        String ret = "";
        for (final Map.Entry<Item, Integer> pair : this.items.entrySet()) {
            ret = ret + "[ Item: " + pair.getKey().toString() + " - Number: " + pair.getValue() + "]\n";
        }
        if (ret.equals("")) {
            ret = "Empty\n";
        }
        return ret;
    }

    @Override
    public List<Item> getAll() {
        return Collections.unmodifiableList(this.items.keySet().stream().collect(Collectors.toList()));
    }

    @Override
    public int getQuantity(final Item item) {
        Objects.requireNonNull(item);
        if (!this.items.containsKey(item)) {
            return 0;
        } else {
            return this.items.get(item);
        }
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = items.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof InventoryImpl) {
            final InventoryImpl other = (InventoryImpl) obj;
            return this.getAll().equals(other.getAll());
        }
        return false;
    }
}
