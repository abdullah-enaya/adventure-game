package AdventureModel.Bosses;

import AdventureModel.BossFight;

public abstract class CurrentMove {
    BossFight bossFight;
    public CurrentMove(BossFight bossFight) {
        this.bossFight = bossFight;
    }
    public abstract void doNextMove();
}
