package AdventureModel.character;

import java.io.IOException;

public class CharacterFactory {

    public Character getCharacter(String name) throws IOException{
        return switch (name){
            case "Tank" -> new Tank();
            case "Damage" -> new Damage();
            case "Mage" -> new Mage();
            default -> null;
        };
    }
}
