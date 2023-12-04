package AdventureModel.character;

import AdventureModel.HealthPoints;

import java.util.ArrayList;

//Tank character class
public class Tank extends Character{

    /**
     * Damage character class initialized with unique attribute values
     */
    public Tank() {
        this.title = "Dwarf";
        this.health = new HealthPoints(150, 3, 150);
        this.attackDamage = 5;
        this.attackRange = 1;
        this.equippedItems = new ArrayList<>();
        this.specialItems = new ArrayList<>();
        this.backstory = "Dwarf";
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
