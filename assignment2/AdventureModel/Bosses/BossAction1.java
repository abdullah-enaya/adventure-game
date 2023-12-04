package AdventureModel.Bosses;

import AdventureModel.BossFight;

public class BossAction1 extends CurrentMove {

    public BossAction1(BossFight bossFight) {
        super(bossFight);
    }

    @Override
    public void doNextMove() {
        this.bossFight.boss.hp += 1;

        this.bossFight.currentMove = new BossAction2(this.bossFight);
    }
}
