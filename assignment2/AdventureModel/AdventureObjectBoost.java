package AdventureModel;

public class AdventureObjectBoost extends AdventureObject {

    private int boostAttack;

    private String characterSpecific;

    /**
     * Adventure Object Constructor
     * ___________________________
     * This constructor sets the name, description, and location of the object.
     *
     * @param name        The name of the Object in the game.
     * @param description One line description of the Object.
     * @param location    The location of the Object in the game.
     * @param xp
     * @param level
     */
    public AdventureObjectBoost(String name, String description, Room location, int xp, int level) {
        super(name, description, location, xp, level);
        this.boostAttack = 0;
        this.characterSpecific = null;
    }

    /**
     * Getter method for boost attack
     * @return the attack of a specific item (int)
     */
    public int getBoostAttack(){
        return this.boostAttack;
    }

    /**
     * Getter method for character specific
     * @return the character the item is specific to in a string
     */
    public String getCharacterSpecific(){
        return this.characterSpecific;
    }

    /**
     * Set the items attack boost amount (int)
     * @param attack
     */
    public void setBoostAttack(int attack){
        this.boostAttack = attack;
    }

    /**
     * Set who the item is specific to
     * @param character
     */
    public void setCharacterSpecific(String character){
        this.characterSpecific = character;
    }


}
