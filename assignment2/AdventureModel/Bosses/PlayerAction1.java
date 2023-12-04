package AdventureModel.Bosses;


import AdventureModel.BossFight;

public class PlayerAction1 extends CurrentMove {
    public PlayerAction1(BossFight bossFight) {
        super(bossFight);
    }

    @Override
    public void doNextMove() {
        this.bossFight.playerCharacter.hp += 3;

        this.bossFight.currentMove = new PlayerAction2(this.bossFight);
    }
}
