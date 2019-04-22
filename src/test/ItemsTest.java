package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.Test;

import thedd.model.character.CharacterFactory;
import thedd.model.item.ItemRarityImpl;
import thedd.model.item.equipableitem.EquipableItem;
import thedd.model.item.equipableitem.EquipableItemImpl;
import thedd.model.item.equipableitem.EquipableItemType;
import thedd.model.item.usableitem.UsableItem;
import thedd.model.item.usableitem.UsableItemImpl;
import thedd.model.world.Difficulty;

/**
 * This class tests thedd.model.item package.
 */
public class ItemsTest {
    private static final String NAME = "Test Name";
    private static final String DESCRIPTION = "Test Description";

    /**
     * Test of UsableItem.
     */
    @Test
    public void testCreationUsableItem() {
        final UsableItem usable = new UsableItemImpl(0, NAME, ItemRarityImpl.COMMON, DESCRIPTION, false, false);
        assertFalse(usable.isUsableInCombat());
        assertFalse(usable.isUsableOutOfCombat());
        assertFalse(usable.isEquipable());
        assertTrue(usable.isUsable());
        assertNotNull(usable.getAction());
        assertNotNull(usable.getEffectsMultiplier());
        assertEquals(ItemRarityImpl.COMMON, usable.getRarity());
    }

    /**
     * Test of EquipableItem.
     */
    @Test
    public void testCreationEquipableItem() {
        final EquipableItem equipable = new EquipableItemImpl(0, NAME, EquipableItemType.AMULET, ItemRarityImpl.COMMON,
                DESCRIPTION);
        assertTrue(equipable.isEquipable());
        assertFalse(equipable.isUsable());
        assertEquals(ItemRarityImpl.COMMON, equipable.getRarity());
        assertEquals(EquipableItemType.AMULET, equipable.getType());
        assertNotNull(equipable.getAdditionalActions());
    }

    /**
     * Test of Equals on UsableItem.
     */
    @Test
    public void testEqualsUsableItems() {
        final UsableItem usable1 = new UsableItemImpl(0, NAME, ItemRarityImpl.COMMON, DESCRIPTION, false, false);
        final UsableItem usable2 = new UsableItemImpl(0, NAME, ItemRarityImpl.COMMON, DESCRIPTION, false, false);
        assertTrue(usable1.equals(usable2));
    }

    /**
     * Test of Equals on EquipableItem.
     */
    @Test
    public void testEqualsEquipableItems() {
        final EquipableItem equipable1 = new EquipableItemImpl(0, NAME, EquipableItemType.AMULET, ItemRarityImpl.COMMON,
                DESCRIPTION);
        final EquipableItem equipable2 = new EquipableItemImpl(0, NAME, EquipableItemType.AMULET, ItemRarityImpl.COMMON,
                DESCRIPTION);
        assertTrue(equipable1.equals(equipable2));
        equipable2.onEquip(CharacterFactory.createPlayerCharacter(Optional.empty(), Difficulty.EASY));
        assertFalse(equipable1.equals(equipable2));
    }
}
