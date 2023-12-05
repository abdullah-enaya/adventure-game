package AdventureModel.characters.abilities;

import AdventureModel.boss.BossFight;
import AdventureModel.characters.Character;

public abstract class Ability {
    public String name;
    public String description;
    public Character character;
    public double attackBoost;
    public double defenseBoost;
    public double healBoost;
    public boolean isAvailable;

    public Ability(Character character) {
        this.character = character;
        this.attackBoost = 1;
        this.defenseBoost = 1;
        this.healBoost = 1;
        this.isAvailable = true;
    }
    public int attack() {
        useAbility();
        return (int) (character.attackDamage * healBoost);
    }

    public boolean getHit(int damage) {
        useAbility();
        return character.health.removeHP((int) (damage / defenseBoost));
    }

    public void heal() {
        useAbility();
        character.health.addHP((int) (character.healingPoints * healBoost));
    }

    public abstract void activateAbility(BossFight bossFight);

    public abstract void useAbility();
}
