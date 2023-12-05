package AdventureModel.Bosses;

import AdventureModel.BossFight;

public class BossAction2 extends CurrentMove {
    public BossAction2(BossFight bossFight) {
        super(bossFight);
    }

    @Override
    public void doNextMove() {
        this.bossFight.playerCharacter.getHit(this.bossFight.boss.attack());

        this.bossFight.currentMove = new PlayerAction1(this.bossFight);
    }
}
