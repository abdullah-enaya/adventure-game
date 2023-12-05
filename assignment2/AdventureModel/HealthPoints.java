package AdventureModel;

import Exceptions.NegativeXPException;

public class HealthPoints {

    /**
     * current total hp of the player
     */
    public int hp;

    /**
     * number of lives the player has
     */
    public int lives;

    /**
     * The max / default hp of the player depending on the character
     */
    public int maxHP;

    /**
     * init player health points
     * @param health int of player health
     * @param life int of number of player lives
     */
    public HealthPoints(int health, int life, int maxHealth){
        this.hp = health;
        this.lives = life;
        this.maxHP = maxHealth;
    }

    /**
     *
     * @return number of lives
     */
    public int getHp(){
        return this.hp;
    }

    /**
     * Return a string of the HP as a fraction of the XP needed for next level, or just the XP if at max level.
     *
     * @return XP string
     */
    public String getHPString() {
        String hp;
        hp = this.getHp() + "/" + this.maxHP;
        return hp;
    }

    /**
     * Add the given hp to the player. Return true if max HP was reached.
     *
     * @param hp The HP points to add to the player.
     * @return true iff max HP was reached, false otherwise.
     */
    public boolean addHP(int hp) {
        if (hp < 0) {
            throw new NegativeValueException();
        }
        this.hp += hp;

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
     *
     * @return number of lives
     */
    public int getLives(){
        return this.lives;
    }

    public void death(){
    }




}
