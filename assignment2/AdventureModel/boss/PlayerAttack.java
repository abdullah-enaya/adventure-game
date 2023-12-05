package AdventureModel.boss;

public class PlayerAttack implements Command {
    BossFight bossFight;
    String attack;

    public PlayerAttack(BossFight bossFight, String attack) {
        this.bossFight = bossFight;
        this.attack = attack;
    }

    @Override
    public String execute() {
        if (bossFight.boss.getHit(bossFight.player.attack())) {
            this.bossFight.winner = 1;
        }
        return "PLAYER ATTACK " + attack;
    }
}
