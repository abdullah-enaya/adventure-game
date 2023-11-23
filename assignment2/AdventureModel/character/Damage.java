package AdventureModel.character;

import java.util.ArrayList;

public class Damage extends Character {

    public Damage() {
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


