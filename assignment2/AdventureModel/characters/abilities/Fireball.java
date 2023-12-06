package AdventureModel.characters.abilities;

import AdventureModel.boss.BossFight;
import AdventureModel.boss.PlayerAttack;
import AdventureModel.characters.Character;

/**
 * Fireball Ability class.
 */
public class Fireball extends Ability{
    /**
     * Initialize Fireball Ability.
     */
    public Fireball(Character character){
        super(character);
        this.description = "Shoots a fireball from mages hand.";
        this.attackBoost = 5.0;
        this.name = "FIREBALL";
    }

    /**
     * Activate this ability. Shoots a fireball.
     * @param bossFight: The BossFight to activate the ability in.
     */
    @Override
    public void activateAbility(BossFight bossFight) {
        bossFight.commandQueue.add(new PlayerAttack(bossFight, "FIREBALL"));
        bossFight.isPlayerTurn = false;
    }

    /**
     * Use the ability. Resets back to BaseAbility.
     */
    @Override
    public void useAbility() {
        this.character.equippedAbility = this.character.unlockedAbilities.get(0);
    }
}
