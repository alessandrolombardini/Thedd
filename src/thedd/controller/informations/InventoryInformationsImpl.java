package thedd.controller.informations;

import java.util.ArrayList;
import java.util.List;
import thedd.model.item.Item;
import thedd.model.character.BasicCharacter;

/**
 * Implementations of {@link thedd.controller.informations.InventoryInformations}.
 */
public final class InventoryInformationsImpl implements InventoryInformations {

    private final BasicCharacter character;
    private final List<Item> allItemsList;

    /**
     * ControllerImpl's constructor.
     * 
     * @param playerCharacter the player character of which the view load
     *                        informations.
     */
    public InventoryInformationsImpl(final BasicCharacter playerCharacter) {
        this.character = playerCharacter;
        allItemsList = new ArrayList<>();
    }

    @Override
    public String getInventoryItemQuantity(final Item item) {
        return String.valueOf(this.character.getInventory().getQuantity(item));
    }

    @Override
    public List<Item> getAllItemsList() {
        allItemsList.addAll(this.character.getInventory().getAll());
        allItemsList.addAll(this.character.getEquippedItems());
        return this.allItemsList;
    }

    @Override
    public boolean isEquipped(final Item item) {
        return this.character.getEquippedItems().contains(item);
    }
}
