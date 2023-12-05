package AdventureModel;

import java.io.Serializable;

/**
 * The Passage class represents entries in a passage table.
 * A passage keeps track of information about an exit from a room.
 * This includes the direction of the exit and the room number.
 * If the passage is blocked, the passage keeps track of the
 * key required to move along this passage.
 */
public class Passage implements Serializable {

    /**
     * The direction of movement in the game.
     */
    private String direction;

    /**
     * The number of the room that this exit leads to.
     */
    private int destinationRoom;

    /**
     * The name of the object required to move along this passage.
     */
    private String keyName;

    /**
     * The level required to access this passage.
     */
    private int level;


    /**
     * This stores boolean to represent if the passage is blocked.
     */
    private boolean isBlocked;

    /**
     * MotionTableEntry constructor.
     *
     * @param direction A string representation of a direction.
     * @param roomNumber A string representation of a room number.
     * @param level The level required to access this passage.
     */
    public Passage(String direction, String roomNumber, int level) {
        this.direction = direction;
        this.destinationRoom = Integer.parseInt(roomNumber);
        this.level = level;
        this.keyName = null;
    }

    /**
     * MotionTableEntry constructor.
     *
     * @param direction A string representation of a direction.
     * @param roomNumber A string representation of a room number.
     * @param level The level required to access this passage.
     * @param key A string representation of a key if the passage is blocked.
     */
    public Passage(String direction, String roomNumber, int level, String key) {
        this.direction = direction;
        this.destinationRoom = Integer.parseInt(roomNumber);
        this.level = level;
        this.keyName = key;
        this.isBlocked = true;
    }

    /**
     * Returns the direction associated with this motion table entry.
     *
     * @return The direction to move in (e.g. "north", "south", etc.).
     */
    public String getDirection() {
        return this.direction;
    }

    /**
     * Returns the number of the room that this motion table entry leads to.
     *
     * @return The number of the destination room.
     */
    public int getDestinationRoom() {
        return this.destinationRoom;
    }

    /**
     * Returns the level required to access this passage.
     *
     * @return The level required to access this passage.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the name of the object required to move along this passage,
     * or null if no object is required.
     *
     * @return The name of the required object, or null if no object is required.
     */
    public String getKeyName() {
        return this.keyName;
    }

    /**
     * Returns if the passage is blocked.
     *
     * @return True if the passage is blocked, false otherwise.
     */
    public boolean getIsBlocked() {
        return this.isBlocked;
    }

    /**
     * Pretty print the Passage
     */
    public void printPassage() {
        System.out.println(this.direction + " " + this.destinationRoom + " " + this.keyName + " " + this.isBlocked);
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
