package AdventureModel.character;

public class CharacterFactory {

    /** Character Factory class, typing in the character creates corresponding class
     *
     * @param name of the file
     * @return the type of character
     */

    public Character getCharacter(String name){
        return switch (name){
            case "Dwarf" -> new Tank();
            case "Damage" -> new Damage();
            case "Mage" -> new Mage();
            default -> null;
        };
    }
}
