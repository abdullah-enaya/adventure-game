package AdventureModel.characters;

import AdventureModel.characters.abilities.BaseAbility;

import java.util.ArrayList;

/**
 * Damage Character class
 */
public class Damage extends Character {
    /**
     * Initialize Damage character class with unique attribute values.
     */
    public Damage() {
        this.title = "Damage";
        this.backstory = "Assassin";
        this.health = new HealthPoints(100, 3, 100);
        this.attackDamage = 15;
        this.healingPoints = 5;
        this.specialItems = new ArrayList<>();
        this.equippedItem = null;
        this.unlockedAbilities = new ArrayList<>();
        this.equippedAbility = new BaseAbility(this);
    }

    /**
     * Updates attributes for the new level.
     * @param level: the level to update to
     * @return String describing the changes.
     */
    @Override
    public String levelUp(int level) {
        return null;
    }
}


