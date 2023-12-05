package AdventureModel.characters.abilities;

import AdventureModel.boss.BossFight;
import AdventureModel.characters.Character;

public class ArmourUp extends Ability{
    int movesLeft;

    public ArmourUp(Character character){
        super(character);
        this.description = "The dwarf shields himself bolstering for an attack. " +
                "He reduces the next two incoming attacks by half";
        this.defenseBoost = 2;
        this.name = "ARMOURUP";
    }

    @Override
    public void activateAbility(BossFight bossFight) {
        this.movesLeft = 2;
        bossFight.view.armourUp();
    }

    @Override
    public void useAbility() {
        this.movesLeft -= 1;
        if (this.movesLeft <= 0) {
            this.character.equippedAbility = this.character.unlockedAbilities.get(0);
        }
    }
}
