package AdventureModel.character;

import java.util.ArrayList;

public class Mage extends Character {

    public Mage() {
        this.title = "Mage";
        this.hp = 125;
        this.attackDamage = 3;
        this.attackRange = 10;
        this.equippedItems = new ArrayList<>();
        this.specialItems = new ArrayList<>();
        this.backstory = "Mage";
        this.specialAbilities = new ArrayList<>();
    }

    @Override
    public int getHit(int mob) {
        this.hp = this.hp - mob;

        return this.hp;
    }

    @Override
    public int attack(int mob) {
        mob = mob - this.attackDamage;

        return mob;
    }

    public void playMinigame(){

    }

}
