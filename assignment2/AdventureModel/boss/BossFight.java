package AdventureModel.boss;

import AdventureModel.characters.Character;
import AdventureModel.characters.abilities.Ability;
import views.BossFightView;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BossFight {
    public BossFightView view;
    public Boss boss;
    public Character player;
    public boolean isPlayerTurn;
    public LinkedBlockingQueue<Command> commandQueue;
    public int winner;

    public BossFight(Boss boss, Character playerCharacter, BossFightView view) {
        this.boss = boss;
        this.player = playerCharacter;
        this.isPlayerTurn = false;
        this.view = view;
        this.commandQueue = new LinkedBlockingQueue<>();
        this.winner = 0;
    }

    public boolean equipPlayerAbility(Ability ability) {
        if (player.unlockedAbilities.contains(ability) && ability.isAvailable) {
            this.player.equipAbility(ability);
            ability.activateAbility(this);
            return true;
        } else return false;
    }

    public int checkIfDone() {
        return winner;
    }
}
