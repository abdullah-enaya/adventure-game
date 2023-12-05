package AdventureModel.boss;

public class BossHeal implements Command {
    BossFight bossFight;

    public BossHeal(BossFight bossFight) {
        this.bossFight = bossFight;
    }

    @Override
    public String execute() {
        bossFight.boss.heal();
        return "BOSS HEAL";
    }
}
