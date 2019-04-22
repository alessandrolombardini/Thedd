package thedd.model.character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

import thedd.model.character.inventory.Inventory;
import thedd.model.character.inventory.InventoryImpl;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.StatValuesImpl;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.actor.automatic.AbstractAutomaticActor;
import thedd.model.combat.modifier.DamageModifier;
import thedd.model.combat.modifier.HitChanceModifier;
import thedd.model.combat.modifier.Modifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.modifier.StatBasedModifier;
import thedd.model.combat.requirements.tags.EffectTagsRequirement;
import thedd.model.combat.requirements.tags.TagRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.tag.EffectTag;
import thedd.model.combat.tag.Tag;
import thedd.model.item.Item;
import thedd.model.item.equipableitem.EquipableItem;
import thedd.model.item.equipableitem.EquipableItemType;

/**
 * Implementation of {@link thedd.model.character.BasicCharacter}.
 */
public class BasicCharacterImpl extends AbstractAutomaticActor implements BasicCharacter {

    private final EnumMap<Statistic, StatValues> stat;
    private final Inventory inventory;
    private final List<EquipableItem> equipment;
    private int hash;

    /**
     * GenericCharacter's constructor.
     * 
     * @param name            the name of the character.
     * @param multiplier      rate multiplied at the basic statistics.
     * @param isInPlayerParty true if the actor is part of the player's party
     */
    protected BasicCharacterImpl(final String name, final double multiplier, final boolean isInPlayerParty) {
        super(name, isInPlayerParty);
        this.stat = new EnumMap<>(Statistic.class);
        setBasicStat(multiplier);
        this.inventory = new InventoryImpl();
        this.equipment = new ArrayList<>();

        setCommonStatBasedModifiers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return this.stat.get(Statistic.HEALTH_POINT).getActual() > 0;
    }

    @Override
    public final StatValues getStat(final Statistic stat) {
        Objects.requireNonNull(stat);
        return this.stat.get(stat);
    }

    @Override
    public final EnumMap<Statistic, StatValues> getAllStat() {
        final EnumMap<Statistic, StatValues> ret = new EnumMap<>(Statistic.class);
        this.stat.entrySet().forEach(el -> {
            ret.put(el.getKey(), el.getValue());
        }); // done a defense copy
        return ret;
    }

    @Override
    public final boolean equipItem(final Item item) {
        Objects.requireNonNull(item);
        if (item.isEquipable()) {
            final EquipableItem equipItem = (EquipableItem) item;
            if (isItemEquipableOnEquipment(equipItem)) {
                this.inventory.removeItem(item);
                equipItem.onEquip(this);
                this.equipment.add(equipItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean unequipItem(final Item item) {
        Objects.requireNonNull(item);
        final int index = equipment.indexOf(item);
        if (index == -1) {
            return false;
        }
        this.equipment.get(index).onUnequip(this);
        this.inventory.addItem(equipment.remove(index));
        return true;
    }

    @Override
    public final List<EquipableItem> getEquippedItems() {
        return Collections.unmodifiableList(this.equipment);
    }

    @Override
    public final Inventory getInventory() {
        return this.inventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        return this.stat.get(Statistic.AGILITY).getActual();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Name: " + this.getName() + " - Stat: " + this.stat + "\nEquipment: " + this.equipment + " - Inventory: "
                + this.inventory.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = equipment.hashCode() ^ inventory.hashCode() ^ stat.hashCode() ^ super.hashCode();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof BasicCharacterImpl) {
            final BasicCharacterImpl other = (BasicCharacterImpl) obj;
            return inventory.equals(other.getInventory()) && stat.equals(other.getAllStat())
                    && getEquippedItems().equals(other.getEquippedItems());
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isItemEquipableOnEquipment(final EquipableItem item) {
        if (item.getType().isWeapon()) {
            if (this.getEquippedItems().stream().noneMatch(it -> it.getType() == EquipableItemType.TWO_HANDED)) {
                final int oneHandWeapons = (int) this.getEquippedItems().stream()
                        .filter(it -> it.getType() == EquipableItemType.ONE_HANDED).count();
                return (oneHandWeapons == 0 || (oneHandWeapons == 1 && item.getType() == EquipableItemType.ONE_HANDED));
            }
            return false;
        } else if (item.getType() == EquipableItemType.RING) {
            return ((int) this.getEquippedItems().stream().filter(it -> it.getType() == EquipableItemType.RING)
                    .count() < EquipableItemType.getMaxNumOfRings());
        } else {
            return this.getEquippedItems().stream().noneMatch(it -> it.getType() == item.getType());
        }
    }

    // This method sets basic statistics of the character.
    private void setBasicStat(final double multiplier) {
        double value = Statistic.HEALTH_POINT.getBasicValue() * multiplier;
        this.stat.put(Statistic.HEALTH_POINT, new StatValuesImpl((int) value, (int) value));
        value = Statistic.AGILITY.getBasicValue() * multiplier;
        this.stat.put(Statistic.AGILITY, new StatValuesImpl((int) value, StatValuesImpl.NO_MAX));
        value = Statistic.CONSTITUTION.getBasicValue() * multiplier;
        this.stat.put(Statistic.CONSTITUTION, new StatValuesImpl((int) value, StatValuesImpl.NO_MAX));
        value = Statistic.STRENGTH.getBasicValue() * multiplier;
        this.stat.put(Statistic.STRENGTH, new StatValuesImpl((int) value, StatValuesImpl.NO_MAX));
    }

    private void setCommonStatBasedModifiers() {
        final ModifierActivation offensive = ModifierActivation.ACTIVE_ON_ATTACK;
        final ModifierActivation defensive = ModifierActivation.ACTIVE_ON_DEFENCE;
        List<Tag> requiredTags = new ArrayList<Tag>();
        List<Tag> allowedTags = new ArrayList<Tag>();

        // Resistance to poison per CONSTITUTION point
        final Modifier<ActionEffect> cosPoisonResistance = new StatBasedModifier<>(Statistic.CONSTITUTION, this,
                                                       new DamageModifier(-0.025, true, true, defensive));
        requiredTags = Arrays.asList(EffectTag.POISON_DAMAGE);
        cosPoisonResistance.addRequirement(new TagRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));

        // Bonus to damage per STRENGTH point
        final Modifier<ActionEffect> strDamage = new StatBasedModifier<>(Statistic.STRENGTH, this,
                new DamageModifier(2, false, false, offensive));
        requiredTags = Arrays.asList(EffectTag.NORMAL_DAMAGE);
        strDamage.addRequirement(new TagRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));

        // Bonus to hit chance per AGILITY point
        final Modifier<Action> dexHitChance = new StatBasedModifier<>(Statistic.AGILITY, this,
                new HitChanceModifier(0.015, true, offensive));
        requiredTags = Arrays.asList(EffectTag.NORMAL_DAMAGE);
        dexHitChance.addRequirement(new EffectTagsRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));

        // Bonus to chances of being missed by a physical attack per AGILITY point
        final Modifier<Action> dexMissChance = new StatBasedModifier<>(Statistic.AGILITY, this,
                new HitChanceModifier(-0.015, true, defensive));
        requiredTags = Arrays.asList(EffectTag.NORMAL_DAMAGE);
        dexMissChance.addRequirement(new EffectTagsRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));

        // Resistance to physical damage per CONSTITUTION point
        final Modifier<ActionEffect> cosDmgResistance = new StatBasedModifier<>(Statistic.CONSTITUTION, this,
                new DamageModifier(-0.025, true, true, defensive));
        allowedTags = Arrays.asList(EffectTag.NORMAL_DAMAGE, EffectTag.AP_DAMAGE);
        cosDmgResistance.addRequirement(new TagRequirement<>(false, TagRequirementType.ALLOWED, allowedTags));

        addActionModifier(dexMissChance, true);
        addActionModifier(dexHitChance, true);
        addEffectModifier(cosDmgResistance, true);
        addEffectModifier(strDamage, true);
        addEffectModifier(cosPoisonResistance, true);
    }
}
