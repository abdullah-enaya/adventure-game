package AdventureModel;

import Exceptions.NegativeXPException;

/**
 * Class Level.
 * This class stores the level and XP of the player, and handles updates to level and xp.
 */
public class Level {
    /**
     * The xp of the player after the current level.
     */
    private int xp;
    /**
     * The current level of the player.
     */
    private int level;
    /**
     * The xp between this level and the next level, or 0 if this is the max level.
     */
    private int xpToNextLevel;
    /**
     * An array of the XP needed to move between the levels.
     */
    private int[] reqXPs;
    /**
     * The maximum level possible.
     */
    private int maxLevel;

    /**
     * Adventure Game Level Constructor.
     *
     * @param level The current level of the player
     * @param reqXPs A list of XP points required between the levels. reqXP[0] is from Level 1 -> 2, reqXP[1] is from Level 2 -> 3, and so on.
     */
    public Level(int level, int[] reqXPs) {
        this.xp = 0;
        this.level = level;
        this.reqXPs = reqXPs;
        this.maxLevel = reqXPs.length + 1;
        if (level >= this.maxLevel) {
            this.xpToNextLevel = 0;
        }
        else {
            this.xpToNextLevel = this.reqXPs[level - 1];
        }
    }

    /**
     * Add the given xp to the player, updating the level when needed. Return true if level was changed.
     *
     * @param xp The XP points to add to the player.
     * @return true iff level has changed, false otherwise.
     */
    public boolean addXP(int xp) {
        if (xp < 0) {
            throw new NegativeXPException();
        }
        this.xp += xp;

        boolean changed = false;
        while (this.xpToNextLevel != 0 && this.xp >= this.xpToNextLevel) {
            this.xp -= this.xpToNextLevel;
            this.incrementLevel();
            changed = true;
        }
        return changed;
    }

    /**
     * Remove the given xp to the player, updating the level when needed. Return true if level was changed.
     *
     * @param xp The XP points to remove from the player.
     * @return true iff level has changed, false otherwise.
     */
    public boolean removeXP(int xp) {
        if (xp < 0) {
            throw new NegativeXPException();
        }
        this.xp -= xp;

        boolean changed = false;
        while (this.level > 1 && this.xp <= 0) {
            this.decrementLevel();
            this.xp += this.xpToNextLevel;
            changed = true;
        }
        return changed;
    }

    /**
     * Increment the level. Doesn't update XP.
     */
    private void incrementLevel() {
        this.level += 1;
        if (this.level >= this.maxLevel) {
            this.xpToNextLevel = 0;
        }
        else {
            this.xpToNextLevel = this.reqXPs[this.level - 1];
        }
    }

    /**
     * Decrement the level. Doesn't update XP.
     */
    private void decrementLevel() {
        this.level -= 1;
        this.xpToNextLevel = this.reqXPs[this.level - 1];
    }

    /**
     * Getter method for the xp attribute.
     *
     * @return The current xp of the player, above the current level.
     */
    public int getXP() {
        return this.xp;
    }

    /**
     * Return a string of the XP as a fraction of the XP needed for next level, or just the XP if at max level.
     *
     * @return XP string
     */
    public String getXPString() {
        String xp;
        if (this.isMaxLevel()) {
            xp = Integer.toString(this.getXP());
        } else {
            xp = this.getXP() + "/" + this.getXPToNextLevel();
        }
        return xp;
    }

    /**
     * Getter method for the leve attribute.
     *
     * @return The current level of the player.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Getter method for the xp to next level attribute.
     *
     * @return The xp needed for the player to move between the current and next level, or 0 if at max level.
     */
    public int getXPToNextLevel() {
        return this.xpToNextLevel;
    }

    /**
     * Returns whether the level is at its maximum.
     *
     * @return true iff the current level is the max level, false otherwise.
     */
    public boolean isMaxLevel() {
        return this.xpToNextLevel == 0;
    }

    /**
     * Return a message of the level up to the current level.
     *
     * @return text for leveling up.
     */
    public String levelUpText() {
        return "You have leveled up!";
    }
}
