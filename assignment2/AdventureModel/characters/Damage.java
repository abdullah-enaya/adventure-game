package AdventureModel.characters;

import AdventureModel.characters.abilities.BaseAbility;

import java.util.ArrayList;

//Damage character class
public class Damage extends Character {
/**
 * Damage character class initialized with unique attribute values
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

    @Override
    public void levelUp(int level) {
    }
}


