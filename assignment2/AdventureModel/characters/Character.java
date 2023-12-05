package AdventureModel.characters;

import AdventureModel.AdventureObject;
import AdventureModel.characters.abilities.Ability;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Parent class for the characters. All character types will inherit from this class.
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

    public void getHit(int damage) {
        this.equippedAbility.getHit(damage);
    }

    public int attack() {
        return this.equippedAbility.attack();
    }

    public void heal() {
        this.equippedAbility.heal();
    }

    public abstract void levelUp(int level);

    public void equipAbility(Ability ability) {
        equippedAbility = ability;
    }

    public Ability getAbility(String abilityName) {
        for (Ability ability: unlockedAbilities) {
            if ((ability.name != null) && (ability.name.equals(abilityName))) {
                return ability;
            }
        }
        return null;
    }
}
