package AdventureModel;

import AdventureModel.Bosses.BossAction1;
import AdventureModel.Bosses.CurrentMove;
import AdventureModel.character.Character;
import javafx.scene.Node;

public class BossFight {
    Node view;

    public Boss boss;
    public Character playerCharacter;
    public CurrentMove currentMove;

    public BossFight(Boss boss, Character playerCharacter) {
        this.boss = boss;
        this.playerCharacter = playerCharacter;
        this.currentMove = new BossAction1(this);
    }

    public void doNextMove() {
        this.currentMove.doNextMove();
    }

    public void doNextTwoMoved() {
        this.doNextMove();
        this.doNextMove();
    }
}
