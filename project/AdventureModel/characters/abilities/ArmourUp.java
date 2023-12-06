package AdventureModel.characters.abilities;

import AdventureModel.boss.BossFight;
import AdventureModel.characters.Character;

/**
 * ArmourUp Ability class.
 */
public class ArmourUp extends Ability{
    private int movesLeft;

    /**
     * Initializes ArmourUp ability.
     */
    public ArmourUp(Character character){
        super(character);
        this.description = "The dwarf shields himself bolstering for an attack. " +
                "He reduces the next two incoming attacks by half";
        this.defenseBoost = 2;
        this.name = "ARMOURUP";
    }

    /**
     * Activate this ability. Initialize the number of moves it lasts.
     * @param bossFight: The BossFight to activate the ability in.
     */
    @Override
    public void activateAbility(BossFight bossFight) {
        this.movesLeft = 4;
        bossFight.isPlayerTurn = false;
    }

    /**
     * Use the ability. Disables after 2 rounds.
     */
    @Override
    public void useAbility() {
        this.movesLeft -= 1;
        if (this.movesLeft <= 0) {
            this.character.equippedAbility = this.character.unlockedAbilities.get(0);
        }
    }
}
