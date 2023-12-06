package AdventureModel.characters;

import AdventureModel.characters.abilities.ArmourUp;
import AdventureModel.characters.abilities.BaseAbility;

import java.util.ArrayList;

/**
 * Dwarf Character class
 */
public class Dwarf extends Character{
    /**
     * Initialize Dwarf character class with unique attribute values
     */
    public Dwarf() {
        this.title = "Dwarf";
        this.backstory = "Dwarf";
        this.health = new HealthPoints(150, 3, 150);
        this.attackDamage = 50;
        this.healingPoints = 5;
        this.specialItems = new ArrayList<>();
        this.equippedItem = null;
        this.unlockedAbilities = new ArrayList<>();
        this.unlockedAbilities.add(new BaseAbility(this));
        this.unlockedAbilities.add(new ArmourUp(this));
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
