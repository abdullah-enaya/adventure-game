package AdventureModel.character;

import AdventureModel.AdventureObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Parent class for the characters. All character types will inherit from this class.
 */
public abstract class Character implements Serializable {
    public String title;
    public int hp;
    public int attackDamage;
    public double attackRange;
    public ArrayList<AdventureObject> equippedItems;
    public ArrayList<AdventureObject> specialItems;
    public String backstory;
    public ArrayList<Ability> specialAbilities;
    public abstract int getHit(int mob);

    public abstract int attack();


}
