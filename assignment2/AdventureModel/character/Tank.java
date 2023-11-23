package AdventureModel.character;

import java.util.ArrayList;

public class Tank extends Character{

    public Tank() {
        this.title = "Tank";
        this.hp = 150;
        this.attackDamage = 5;
        this.attackRange = 1;
        this.equippedItems = new ArrayList<>();
        this.specialItems = new ArrayList<>();
        this.backstory = "Dwarf";
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
