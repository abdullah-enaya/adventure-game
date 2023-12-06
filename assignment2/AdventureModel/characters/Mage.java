package AdventureModel.characters;

import AdventureModel.characters.abilities.BaseAbility;
import AdventureModel.characters.abilities.Fireball;

import java.util.ArrayList;

/**
 * Mage Character class
 */
public class Mage extends Character {
    /**
     * Initialize Mage character class with unique attribute values.
     */
    public Mage() {
        this.title = "Mage";
        this.backstory = "Mage";
        this.health = new HealthPoints(90, 3, 125);
        this.attackDamage = 8;
        this.healingPoints = 5;
        this.specialItems = new ArrayList<>();
        this.equippedItem = null;
        this.unlockedAbilities = new ArrayList<>();
        this.unlockedAbilities.add(new BaseAbility(this));
        this.unlockedAbilities.add(new Fireball(this));
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
