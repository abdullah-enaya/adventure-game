package AdventureModel.character;

import AdventureModel.AdventureObject;

import java.util.ArrayList;

public abstract class Character {
    public String title;
    public int hp;
    public int attackDamage;
    public double attackRange;
    public ArrayList<AdventureObject> equippedItems;
    public ArrayList<AdventureObject> specialItems;
    public String backstory;
    public ArrayList<Ability> specialAbilities;
    public abstract int getHit(int mob);

    public abstract int attack(int mob);

    public abstract void playMinigame();


}
