package AdventureModel.characters;

import AdventureModel.characters.abilities.BaseAbility;

import java.util.ArrayList;

public class Tank extends Character{
    /**
     * Damage character class initialized with unique attribute values
     */
    public Tank() {
        this.title = "Dwarf";
        this.backstory = "Dwarf";
        this.health = new HealthPoints(150, 3, 150);
        this.attackDamage = 5;
        this.healingPoints = 5;
        this.specialItems = new ArrayList<>();
        this.equippedItem = null;
        this.unlockedAbilities = new ArrayList<>();
        this.equippedAbility = new BaseAbility(this);
    }

    @Override
    public void levelUp(int level) {
    }
}
