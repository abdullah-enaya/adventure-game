package AdventureModel.boss;

import AdventureModel.characters.HealthPoints;
import AdventureModel.characters.Character;
import AdventureModel.characters.abilities.BaseAbility;

import java.util.ArrayList;

public class Boss extends Character {

    public Boss() {
        this.title = "Boss";
        this.backstory = "Boss";
        this.health = new HealthPoints(500, 1, 500);
        this.attackDamage = 10;
        this.healingPoints = 1;
        this.specialItems = new ArrayList<>();
        this.equippedItem = null;
        this.unlockedAbilities = new ArrayList<>();
        this.equippedAbility = new BaseAbility(this);
    }

    @Override
    public void levelUp(int level) {
    }
}
