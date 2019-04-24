package thedd.model.character.types;

import java.util.Optional;

import org.apache.commons.lang3.RandomUtils;

import thedd.model.character.BasicCharacterImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.implementations.ActiveDefence;
import thedd.model.combat.action.implementations.DivineIntervention;
import thedd.model.combat.action.implementations.HeavyAttack;
import thedd.model.combat.action.implementations.LightAttack;
import thedd.model.item.Item;
import thedd.model.item.ItemRarityImpl;
import thedd.model.item.equipableitem.implementations.EquipableItemChest;
import thedd.model.item.equipableitem.implementations.EquipableItemHelmet;
import thedd.model.item.equipableitem.implementations.EquipableItemShield;
import thedd.model.item.equipableitem.implementations.EquipableItemSword;
import thedd.model.item.usableitem.UsableItemPotion;

/**
 * Player Character extension of
 * {@link thedd.model.character.BasicCharacterImpl}.
 */
public class PlayerCharacter extends BasicCharacterImpl {

    private static final String DEFAULT_NAME = "Player";
    private static final int BASE_AGILITY = 5;
    private static final int VARIATION_AGILITY = 3;
    private static final int BASE_HEALTH = 80;
    private static final int VARIATION_HEALTH = 30;
    private static final int BASE_CONSTITUTION = 5;
    private static final int VARIATION_CONSTITUTION = 2;
    private static final int BASE_STRENGTH = 6;
    private static final int VARIATION_STRENGTH = 2;

    /**
     * PlayerCharacter's constructor.
     * 
     * @param name the string name of the player character. If this value is
     *             Optional.empty then a default name value will be added.
     */
    public PlayerCharacter(final Optional<String> name) {
        super((name.isPresent() && !name.get().equals("")) ? name.get() : DEFAULT_NAME, true);
        initInventory();
        addActionToAvailable(new LightAttack(TargetType.EVERYONE));
        addActionToAvailable(new HeavyAttack(TargetType.EVERYONE));
        addActionToAvailable(new ActiveDefence());
        addActionToAvailable(new DivineIntervention(TargetType.EVERYONE));
    }

    private void initInventory() {
        this.getInventory().addItem(UsableItemPotion.getNewInstance(ItemRarityImpl.COMMON));
        this.getInventory().addItem(EquipableItemSword.getNewInstance(ItemRarityImpl.COMMON));
        this.getInventory().addItem(EquipableItemShield.getNewInstance(ItemRarityImpl.COMMON));
        this.getInventory().addItem(EquipableItemChest.getNewInstance(ItemRarityImpl.COMMON));
        this.getInventory().addItem(EquipableItemHelmet.getNewInstance(ItemRarityImpl.COMMON));
        this.getInventory().getAll().stream().filter(Item::isEquipable).forEach(item -> this.equipItem(item));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHealthPointBaseValue() {
        return RandomUtils.nextInt(0, VARIATION_HEALTH + 1) + BASE_HEALTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAgilityStatBaseValue() {
        return RandomUtils.nextInt(0, VARIATION_AGILITY + 1) + BASE_AGILITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getConstitutionStatBaseValue() {
        return RandomUtils.nextInt(0, VARIATION_CONSTITUTION + 1) + BASE_CONSTITUTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStrengthStatBaseValue() {
        return RandomUtils.nextInt(0, VARIATION_STRENGTH + 1) + BASE_STRENGTH;
    }

}
