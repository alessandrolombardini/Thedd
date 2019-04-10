package thedd.model.roomevent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import thedd.model.roomevent.combatevent.CombatEvent;
import thedd.model.roomevent.combatevent.CombatEventImpl;
import thedd.model.roomevent.floorchanger.FloorChangerEvent;
import thedd.model.roomevent.floorchanger.Stairs;
import thedd.model.roomevent.interactableactionperformer.Contraption;
import thedd.model.roomevent.interactableactionperformer.ContraptionSanctuary;
import thedd.model.roomevent.interactableactionperformer.ContraptionTrap;
import thedd.model.roomevent.interactableactionperformer.TreasureChest;

/**
 * Factory to create RoomEvents on demand.
 */
public final class RoomEventHelper {

    private static final List<Supplier<Contraption>> CONTRAPTION_DATABASE = new ArrayList<>();
    private static final Random RNG = new Random();

    static {
        CONTRAPTION_DATABASE.add(ContraptionTrap::newInstance);
        CONTRAPTION_DATABASE.add(ContraptionSanctuary::newInstance);
    }

    private RoomEventHelper() {
    }

    /**
     * Returns a {@link thedd.model.roomevent.floorchanger.FloorChangerEvent}.
     * @return
     *  an instance of {@link thedd.model.roomevent.floorchanger.Stairs}.
     */
    public static FloorChangerEvent getStairs() {
        return new Stairs();
    }
    /**
     * Returns a {@link thedd.model.roomevent.combatevent.CombatEvent}.
     * @return
     *  an instance of {@link thedd.model.roomevent.combatevent.CombatEvent}.
     */
    public static CombatEvent getCombat() {
        return new CombatEventImpl();
    }
    /**
     * Returns a {@link thedd.model.roomevent.interactableactionperformer.TreasureChest}.
     * @return
     *  an instance of {@link thedd.model.roomevent.interactableactionperformer.TreasureChest}.
     */
    public static RoomEvent getTreasureChest() {
        return TreasureChest.newInstance();
    }
    /**
     * Returns a {@link thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer}.
     * @return
     *  an instance of {@link thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer}.
     */
    public static RoomEvent getContraption() {
            return CONTRAPTION_DATABASE.get(RNG.nextInt(CONTRAPTION_DATABASE.size())).get();
    }
}
