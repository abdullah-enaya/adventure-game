package AdventureModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class AdventureLoader. Loads an adventure from files.
 */
public class AdventureLoader {

    private AdventureGame game; //the game to return
    private String adventureName; //the name of the adventure

    /**
     * Adventure Loader Constructor
     * __________________________
     * Initializes attributes
     * @param game the game that is loaded
     * @param directoryName the directory in which game files live
     */
    public AdventureLoader(AdventureGame game, String directoryName) {
        this.game = game;
        this.adventureName = directoryName;
    }

     /**
     * Load game from directory
     */
    public void loadGame() throws IOException {
        parseRooms();
        parseObjects();
        parseSynonyms();
        this.game.setHelpText(parseOtherFile("help"));
    }

     /**
     * Parse Rooms File
     */
    private void parseRooms() throws IOException {

        int roomNumber;

        String roomFileName = this.adventureName + File.separator + "rooms.txt";
        BufferedReader buff = new BufferedReader(new FileReader(roomFileName));

        while (buff.ready()) {

            String currRoom = buff.readLine(); // first line is the number of a room

            roomNumber = Integer.parseInt(currRoom); //current room number

            // now need to get room name
            String roomName = buff.readLine();

            // now we need to get the description, and xp first if given
            String roomDescription = "";
            String line = buff.readLine();
            int xp;
            if (line.startsWith("XP ")) {
                xp = Integer.parseInt(line.substring(3));
                line = buff.readLine();
            } else {
                xp = 0;
            }
            while (!line.equals("-----")) {
                roomDescription += line + "\n";
                line = buff.readLine();
            }
            roomDescription += "\n";

            // now we make the room object
            Room room = new Room(roomName, roomNumber, roomDescription, adventureName, xp);

            // now we make the motion table
            line = buff.readLine(); // reads the line after "-----"
            while (line != null && !line.equals("")) {
                String[] part = line.split(" \s+"); // have to use regex \\s+ as we don't know how many spaces are between the direction and the room number
                String direction = part[0];
                String dest = part[1];
                int level;
                if (dest.contains("|L")) {
                    String[] levelPath = dest.split("\\|L");
                    level = Integer.parseInt(levelPath[1]);
                    dest = levelPath[0];
                }
                else {
                    level = 0;
                }
                if (dest.contains("/")) {
                    String[] blockedPath = dest.split("[/|]");
                    String dest_part = blockedPath[0];
                    String object = blockedPath[1];
                    Passage entry = new Passage(direction, dest_part, level, object);
                    room.getMotionTable().addDirection(entry);
                } else {
                    Passage entry = new Passage(direction, dest, level);
                    room.getMotionTable().addDirection(entry);
                }
                line = buff.readLine();
            }
            this.game.getRooms().put(room.getRoomNumber(), room);
        }

    }

     /**
     * Parse Objects File
     */
    public void parseObjects() throws IOException {

        
        String objectFileName = this.adventureName + File.separator + "objects.txt";
        BufferedReader buff = new BufferedReader(new FileReader(objectFileName));

        while (buff.ready()) {
            String objectName = buff.readLine();
            String objectDescription = buff.readLine();
            String objectLocation = buff.readLine();
            int objectXP;
            if (objectLocation.startsWith("XP ")) {
                objectXP = Integer.parseInt(objectLocation.substring(3));
                objectLocation = buff.readLine();
            } else {
                objectXP = 0;
            }
            int boostAttack;
            if (objectLocation.startsWith("ATTACK ")){
                boostAttack = Integer.parseInt(objectLocation.substring(7));
                objectLocation = buff.readLine();
            }
            else{
                boostAttack = 0;
            }
            String characterSpecific;
            if (objectLocation.startsWith("CHARACTER ")){
                characterSpecific = objectLocation.substring(10);
                objectLocation = buff.readLine();
            }
            else{
                characterSpecific = null;
            }
            String separator = buff.readLine();
            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");
            int level;
            if (objectLocation.contains("/")) {
                String[] blockedObject = objectLocation.split("/L");
                objectLocation = blockedObject[0];
                level = Integer.parseInt(blockedObject[1]);
            } else {
                level = 0;
            }

            int i = Integer.parseInt(objectLocation);

            Room location = this.game.getRooms().get(i);
            AdventureObject object = new AdventureObject(objectName, objectDescription, location, objectXP, level, boostAttack, characterSpecific);
            location.addGameObject(object);

        }

    }

     /**
     * Parse Synonyms File
     */
    public void parseSynonyms() throws IOException {
        String synonymsFileName = this.adventureName + File.separator + "synonyms.txt";
        BufferedReader buff = new BufferedReader(new FileReader(synonymsFileName));
        String line = buff.readLine();
        while(line != null){
            String[] commandAndSynonym = line.split("=");
            String command1 = commandAndSynonym[0];
            String command2 = commandAndSynonym[1];
            this.game.getSynonyms().put(command1,command2);
            line = buff.readLine();
        }

    }

    /**
     * Parse Files other than Rooms, Objects and Synonyms
     *
     * @param fileName the file to parse
     */
    public String parseOtherFile(String fileName) throws IOException {
        String text = "";
        fileName = this.adventureName + "/" + fileName + ".txt";
        BufferedReader buff = new BufferedReader(new FileReader(fileName));
        String line = buff.readLine();
        while (line != null) { // while not EOF
            text += line+"\n";
            line = buff.readLine();
        }
        return text;
    }

}
