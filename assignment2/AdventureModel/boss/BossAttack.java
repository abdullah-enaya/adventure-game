package AdventureModel.boss;

public class BossAttack implements Command {
    BossFight bossFight;
    String attack;

    public BossAttack(BossFight bossFight, String attack) {
        this.bossFight = bossFight;
        this.attack = attack;
    }

    @Override
    public String execute() {
        if (bossFight.player.getHit(bossFight.boss.attack())) {
            this.bossFight.winner = 2;
        }
        return "BOSS ATTACK " + attack;
    }
}
