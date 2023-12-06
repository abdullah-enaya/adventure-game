package AdventureModel.characters.abilities;

import AdventureModel.boss.BossFight;
import AdventureModel.characters.Character;

/**
 * BaseAbility Class
 */
public class BaseAbility extends Ability {
    /**
     * Initialize BaseAbility.
     */
    public BaseAbility(Character character) {
        super(character);
        this.name = null;
        this.description = "No ability equipped.";
    }

    /**
     * Activate this ability.
     * @param bossFight: The BossFight to activate the ability in.
     */
    @Override
    public void activateAbility(BossFight bossFight) {
        bossFight.isPlayerTurn = false;
    }

    /**
     * Use the ability. Do nothing.
     */
    @Override
    public void useAbility() {}
}
