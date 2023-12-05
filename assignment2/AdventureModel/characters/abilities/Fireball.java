package AdventureModel.characters.abilities;

import AdventureModel.boss.BossFight;
import AdventureModel.boss.PlayerAttack;
import AdventureModel.characters.Character;

public class Fireball extends Ability{
    public Fireball(Character character){
        super(character);
        this.description = "Shoots a fireball from mages hand.";
        this.attackBoost = 2.0;
        this.name = "FIREBALL";
    }

    @Override
    public void activateAbility(BossFight bossFight) {
        bossFight.commandQueue.add(new PlayerAttack(bossFight, "FIREBALL")) ;
    }

    @Override
    public void useAbility() {
        this.character.equippedAbility = this.character.unlockedAbilities.get(0);
    }
}
