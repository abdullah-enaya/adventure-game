package AdventureModel.characters;

import AdventureModel.AdventureObject;
import AdventureModel.characters.abilities.Ability;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Parent class for the characters. All character types will inherit from this class.
 * Characters define the attributes for actions during BossFights.
 */
public abstract class Character implements Serializable {
    public String title;
    public String backstory;
    public HealthPoints health;
    public int attackDamage;
    public int healingPoints;
    public ArrayList<AdventureObject> specialItems;
    public AdventureObject equippedItem;
    public ArrayList<Ability> unlockedAbilities;
    public Ability equippedAbility;

    /**
     * Takes damage.
     * @param damage the damage to be taken, in hitpoints.
     * @return true iff the character dies.
     */
    public boolean getHit(int damage) {
        return this.equippedAbility.getHit(damage);
    }

    /**
     * Returns the value of the character's attack.
     */
    public int attack() {
        return this.equippedAbility.attack();
    }

    /**
     * Heals the character.
     */
    public void heal() {
        this.equippedAbility.heal();
    }

    /**
     * Updates attributes for the new level.
     * @param level: the level to update to
     * @return String describing the changes.
     */
    public abstract String levelUp(int level);

    /**
     * Equips the ability given to the current character.
     */
    public void equipAbility(Ability ability) {
        equippedAbility = ability;
    }

    /**
     * Get the Ability from the list of unlockedAbilities by its name. Return null if it's not there.
     */
    public Ability getAbility(String abilityName) {
        for (Ability ability: unlockedAbilities) {
            if ((ability.name != null) && (ability.name.equals(abilityName))) {
                return ability;
            }
        }
        return null;
    }
}
