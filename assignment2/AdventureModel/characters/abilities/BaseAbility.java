package AdventureModel.characters.abilities;

import AdventureModel.boss.BossFight;
import AdventureModel.characters.Character;

public class BaseAbility extends Ability {
    public BaseAbility(Character character) {
        super(character);
        this.name = null;
        this.description = "No ability equipped.";
    }

    @Override
    public void activateAbility(BossFight bossFight) {}

    @Override
    public void useAbility() {}
}
