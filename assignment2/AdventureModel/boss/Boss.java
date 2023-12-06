package AdventureModel.boss;

import AdventureModel.characters.HealthPoints;
import AdventureModel.characters.Character;
import AdventureModel.characters.abilities.BaseAbility;

import java.util.ArrayList;

/**
 * Class Boss.
 * Defines a Boss Character, which the player fights.
 */
public class Boss extends Character {
    /**
     * Boss Initializer. Initializes attributes.
     */
    public Boss() {
        this.title = "Boss";
        this.backstory = "Boss";
        this.health = new HealthPoints(80, 1, 500);
        this.attackDamage = 10;
        this.healingPoints = 1;
        this.specialItems = new ArrayList<>();
        this.equippedItem = null;
        this.unlockedAbilities = new ArrayList<>();
        this.equippedAbility = new BaseAbility(this);
    }

    /**
     * Levels up the character. Doesn't apply to Bosses.
     */
    @Override
    public String levelUp(int level) {
        return null;
    }
}
