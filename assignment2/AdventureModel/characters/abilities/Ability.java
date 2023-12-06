package AdventureModel.characters.abilities;

import AdventureModel.boss.BossFight;
import AdventureModel.characters.Character;

import java.io.Serializable;

/**
 * Ability abstract class
 */
public abstract class Ability implements Serializable {
    public String name;
    public String description;
    public Character character;
    public double attackBoost;
    public double defenseBoost;
    public double healBoost;
    public boolean isAvailable;

    /**
     * Ability initializer. Initialized basic ability, with no boosts.
     */
    public Ability(Character character) {
        this.character = character;
        this.attackBoost = 1;
        this.defenseBoost = 1;
        this.healBoost = 1;
        this.isAvailable = true;
    }

    /**
     * Do attack with this ability's boost.
     * @return attack damage.
     */
    public int attack() {
        useAbility();
        return (int) (character.attackDamage * attackBoost);
    }

    /**
     * Get hit with this ability's boost.
     * @param damage to be dealt.
     * @return true iff Character died.
     */
    public boolean getHit(int damage) {
        useAbility();
        return character.health.removeHP((int) (damage / defenseBoost));
    }

    /**
     * Heal with this ability's boost.
     */
    public void heal() {
        useAbility();
        character.health.addHP((int) (character.healingPoints * healBoost));
    }

    /**
     * Activate this ability.
     * @param bossFight: The BossFight to activate the ability in.
     */
    public abstract void activateAbility(BossFight bossFight);

    /**
     * Use the ability. Called on every move.
     */
    public abstract void useAbility();
}
