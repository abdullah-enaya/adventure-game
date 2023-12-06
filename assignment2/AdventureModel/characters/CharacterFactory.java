package AdventureModel.characters;

/**
 * Character Factory class. Responsible for creating the characters.
 */
public class CharacterFactory {

    /** Creates corresponding class of the type of character given.
     *
     * @param name of the character type
     * @return the type of character
     */
    public Character getCharacter(String name){
        return switch (name){
            case "DWARF" -> new Dwarf();
            case "DAMAGE" -> new Damage();
            case "MAGE" -> new Mage();
            default -> null;
        };
    }
}
