package AdventureModel;

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
     *
     * @return number of lives
     */
    public int getLives(){
        return this.lives;
    }

    public void death(){

    }




}
