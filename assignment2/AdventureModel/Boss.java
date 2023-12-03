package AdventureModel;

import AdventureModel.character.Character;

public class Boss extends Character {

    public Boss() {
        this.title = "Boss";
        this.attackDamage = 50;
        this.hp = 1000;
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
     * @param mob is the mobs hp who is being attacked
     * @return the mobs hp
     */
    @Override
    public int attack(int mob) {
        mob = mob - this.attackDamage;

        return mob;
    }
}
