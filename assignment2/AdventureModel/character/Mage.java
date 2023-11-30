package AdventureModel.character;

import java.util.ArrayList;

public class Mage extends Character {

    public void createMage() {
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
    public void getHit(int mob) {

        this.hp = this.hp - mob;

    }

    @Override
    public void attack(int mob) {
        mob = mob - this.attackDamage;
    }

    public void playMinigame(){

    }

}
