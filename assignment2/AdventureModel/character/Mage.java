package AdventureModel.character;

import AdventureModel.HealthPoints;

import java.util.ArrayList;

public class Mage extends Character {

//Mage character class

    /**
     * Mage character class initialized with unique attribute values
     */
    public Mage() {
        this.title = "Mage";
        this.health = new HealthPoints(125, 3, 125);
        this.attackDamage = 8;
        this.attackRange = 10;
        this.equippedItems = new ArrayList<>();
        this.specialItems = new ArrayList<>();
        this.backstory = "Mage";
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
