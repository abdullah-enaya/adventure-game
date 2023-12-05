package AdventureModel.characters;

import AdventureModel.characters.abilities.BaseAbility;
import AdventureModel.characters.abilities.Fireball;

import java.util.ArrayList;

public class Mage extends Character {
    /**
     * Mage character class initialized with unique attribute values
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

    @Override
    public void levelUp(int level) {
    }
//
//    /**
//     * Responsible for taking damage from mobs / boss
//     * @param mob is mob damage
//     * @return character hp
//     */
//    @Override
//    public int getHit(int mob) {
//        this.health.hp = this.health.hp - mob;
//
//        return this.health.hp;
//    }
//
//    /**
//     * Responsible for attacking mob with unique attack value
//     * @return the mobs hp
//     */
//    @Override
//    public int attack() {
//        return this.attackDamage;
//    }
//
//    public void playMinigame(){
//
//    }
}
