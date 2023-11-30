package AdventureModel.character;

import java.util.ArrayList;

public class Damage extends Character {

    public void createDamage() {
        this.title = "Damage";
        this.hp = 100;
        this.attackDamage = 15;
        this.attackRange = 3;
        this.equippedItems = new ArrayList<>();
        this.specialItems = new ArrayList<>();
        this.backstory = "Assassin";
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


