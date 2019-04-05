package thedd.model.combat.action.effect;

import model.combat.interfaces.ActionActor;
import model.combat.interfaces.ActionEffect;
import model.item.Item;
import model.item.ItemFactory;

/**
 * {@link model.combat.interfaces.ActionEffect} specialization which allow to add an {@link model.item.Item} to a target inventory.
 *
 */
public final class ItemGiverEffect implements ActionEffect {
    //If a luck-like statistic is implemented,
    //then the item can change based on that value with method updateEffectByTarget
    private final Item itemGiven;
    /**
     * 
     */
    public ItemGiverEffect() {
        itemGiven = ItemFactory.getRandomItem();
    }

    @Override
    public void apply(final ActionActor target) {
        if (target instanceof model.character.Character) {
            ((model.character.Character) target).getInventory().addItem(itemGiven);
        } else {
            throw new IllegalArgumentException("This effect can be applied only to ActionActor of type Character");
        }
    }

    /**
     * Unused for this type of ActionEffect with no 'luck'-like stat present in characters.
     */
    @Override
    public void updateEffectByTarget(final ActionActor target) {

    }

    /**
     * Unused for this type of ActionEffect with no 'luck'-like stat present in characters.
     */
    @Override
    public void updateEffectBySource(final ActionActor source) {

    }

    @Override
    public String getLogMessage() {
        return " found " + itemGiven.getName() + ".";
    }

}
