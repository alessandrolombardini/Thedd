package thedd.controller.information;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import thedd.model.item.Item;
import thedd.model.item.equipableitem.EquipableItem;
import thedd.model.character.BasicCharacter;
import thedd.model.combat.action.Action;

/**
 * Implementations of {@link thedd.controller.information.PlayerInformation}.
 */
public final class PlayerInformationImpl implements PlayerInformation {

    private final BasicCharacter character;
    private Optional<Item> usedItem;

    /**
     * ControllerImpl's constructor.
     * 
     * @param playerCharacter the player character of which the view load
     *                        informations.
     */
    public PlayerInformationImpl(final BasicCharacter playerCharacter) {
        this.character = playerCharacter;
        this.usedItem = Optional.empty();
    }

    @Override
    public String getInventoryItemQuantity(final Item item) {
        return String.valueOf(this.character.getInventory().getQuantity(item));
    }

    @Override
    public List<Item> getAllItemsList() {
        final List<Item> allItemsList = new ArrayList<>();
        allItemsList.addAll(this.character.getEquippedItems());
        allItemsList.addAll(this.character.getInventory().getAll());
        return Collections.unmodifiableList(allItemsList);
    }

    @Override
    public boolean isEquipped(final Item item) {
        if (item.isUsable()) {
            return false;
        }
        return this.character.getEquippedItems().contains(item);
    }

    @Override
    public void setUsedItem(final Item item) {
        this.usedItem = Optional.of(item);
    }

    @Override
    public Optional<Item> getUsedItem() {
        return this.usedItem;
    }

    @Override
    public void resetUsedItem() {
        this.usedItem = Optional.empty();
    }

    @Override
    public List<Action> getPlayerActions() {
        return this.character.getAvailableActionsList();
    }

    @Override
    public boolean isItemEquipableOnEquipment(final Item item) {
        if (item.isEquipable()) {
            return this.character.isItemEquipableOnEquipment((EquipableItem) item);
        }
        return false;
    }
}
