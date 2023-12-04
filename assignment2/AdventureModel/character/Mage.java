package AdventureModel.character;

import java.util.ArrayList;

public class Mage extends Character {

//Mage character class

    /**
     * Mage character class initialized with unique attribute values
     */
    public Mage() {
        this.title = "Mage";
        this.hp = 125;
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
        this.hp = this.hp - mob;

        return this.hp;
    }

    /**
     * Responsible for attacking mob with unique attack value
     * @return the mobs hp
     */
    @Override
    public int attack() {
        return this.attackDamage;
    }

    public void playMinigame(){

    }

}
