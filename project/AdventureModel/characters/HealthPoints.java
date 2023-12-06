package AdventureModel.characters;

import Exceptions.NegativeValueException;

import java.io.Serializable;

public class HealthPoints implements Serializable {

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
     * @param lives int of number of player lives
     */
    public HealthPoints(int health, int lives, int maxHealth){
        this.hp = health;
        this.lives = lives;
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

        if (this.hp >= maxHP) {
            this.hp = maxHP;
            return true;
        } else return false;
    }

    /**
     * Remove the given hp from the player, losing a life if hp reaches 0, and returns true if so.
     *
     * @param hp The HP points to remove from the player.
     * @return true iff a live was lost, false otherwise.
     */
    public boolean removeHP(int hp) {
        if (hp < 0) {
            throw new NegativeValueException();
        }
        this.hp -= hp;

        if (this.hp <= 0) {
            loseALife();
            return true;
        } else return false;
    }

    /**
     * Loses a life, resets HP
     */
    private void loseALife() {
        this.lives -= 1;
        this.hp = maxHP;
    }

    /**
     *
     * @return number of lives
     */
    public int getLives(){
        return this.lives;
    }
}
