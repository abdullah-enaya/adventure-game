package AdventureModel.character;

import AdventureModel.HealthPoints;

import java.util.ArrayList;

//Damage character class
public class Damage extends Character {
/**
 * Damage character class initialized with unique attribute values
 */
    public Damage() {
        this.title = "Damage";
        this.health = new HealthPoints(100, 3, 100);
        this.attackDamage = 15;
        this.attackRange = 3;
        this.equippedItems = new ArrayList<>();
        this.specialItems = new ArrayList<>();
        this.backstory = "Assassin";
        this.specialAbilities = new ArrayList<>();
    }

    /**
     * Responsible for taking damage from mobs / boss
     * @param mob is mob damage
     * @return character hp
     */
    @Override
    public int getHit(int mob) {

        this.health.hp = this.health.hp - mob;

        return this.health.hp;

    }

    /**
     * Responsible for attacking mob with unique attack value
     * @param mob is the mobs hp who is being attacked
     * @return the mobs hp
     */
    @Override
    public int attack(int mob) {
        mob = mob - this.attackDamage;

        return mob;
    }
    public void playMinigame(){

    }

}


