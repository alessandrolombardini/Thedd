package thedd.controller;

import java.util.ArrayList;
import java.util.List;
import thedd.model.item.Item;
import thedd.model.character.BasicCharacter;
import thedd.model.character.statistics.Statistic;

/**
 * Implementations of {@link thedd.controller.CharacterInformations}.
 */
public final class CharacterInformationsImpl implements CharacterInformations {

    private final BasicCharacter character;
    private final List<Item> allItemsList;

    /**
     * ControllerImpl's constructor.
     * 
     * @param character the character of which the view load informations.
     */
    public CharacterInformationsImpl(final BasicCharacter character) {
        this.character = character;
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
        System.out.println(allItemsList);
        return this.allItemsList;
    }

    @Override
    public boolean isEquipped(final Item item) {
        return this.character.getEquippedItems().contains(item);
    }

    @Override
    public String getHealthPointValue() {
        return String.valueOf(this.character.getStat(Statistic.HEALTH_POINT).getActual());
    }

    @Override
    public String getConstitutionValue() {
        return String.valueOf(this.character.getStat(Statistic.CONSTITUTION).getActual());
    }

    @Override
    public String getStrengthValue() {
        return String.valueOf(this.character.getStat(Statistic.STRENGTH).getActual());
    }

    @Override
    public String getAgilityValue() {
        return String.valueOf(this.character.getStat(Statistic.AGILITY).getActual());
    }

    @Override
    public String getHealthPointMaxValue() {
        return String.valueOf(this.character.getStat(Statistic.HEALTH_POINT).getMax());
    }

    @Override
    public String getConstitutionMaxValue() {
        return String.valueOf(this.character.getStat(Statistic.CONSTITUTION).getMax());
    }

    @Override
    public String getStrengthMaxValue() {
        return String.valueOf(this.character.getStat(Statistic.STRENGTH).getMax());
    }

    @Override
    public String getAgilityMaxValue() {
        return String.valueOf(this.character.getStat(Statistic.AGILITY).getMax());
    }

}
