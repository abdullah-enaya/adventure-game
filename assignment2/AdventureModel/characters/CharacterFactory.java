package AdventureModel.characters;

public class CharacterFactory {

    /** Character Factory class, typing in the character creates corresponding class
     *
     * @param name of the file
     * @return the type of character
     */

    public Character getCharacter(String name){
        return switch (name){
            case "DWARF" -> new Tank();
            case "DAMAGE" -> new Damage();
            case "MAGE" -> new Mage();
            default -> null;
        };
    }
}
