package AdventureModel;

import Exceptions.NegativeXPException;

public class Level {
    private int xp;
    private int level;

    private int xpToNextLevel;

    private int[] reqXPs;

    private int maxLevel;

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

    public boolean setXP(int xp) {
        int delta = xp - this.xp;
        if (delta >= 0) {
            return this.addXP(delta);
        }
        else {
            return this.removeXP(-delta);
        }
    }

    private void incrementLevel() {
        this.level += 1;
        if (this.level >= this.maxLevel) {
            this.xpToNextLevel = 0;
        }
        else {
            this.xpToNextLevel = this.reqXPs[this.level - 1];
        }
    }

    private void decrementLevel() {
        this.level -= 1;
        this.xpToNextLevel = this.reqXPs[this.level - 1];
    }

    public int getXP() {
        return this.xp;
    }
    public int getLevel() {
        return this.level;
    }

    public int getXPToNextLevel() {
        return this.xpToNextLevel;
    }
}
