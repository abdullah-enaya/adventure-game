package AdventureModel.Bosses;

import AdventureModel.BossFight;

public class PlayerAction2 extends CurrentMove {
    public PlayerAction2(BossFight bossFight) {
        super(bossFight);
    }

    @Override
    public void doNextMove() {
        this.bossFight.boss.getHit(this.bossFight.playerCharacter.attack());

        this.bossFight.currentMove = new BossAction1(this.bossFight);
        this.bossFight.doNextTwoMoves();
    }
}
